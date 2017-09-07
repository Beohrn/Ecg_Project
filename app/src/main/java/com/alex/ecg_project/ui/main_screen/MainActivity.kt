package com.alex.ecg_project.ui.main_screen

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.alex.ecg_project.R
import com.alex.ecg_project.services.RandomService
import com.alex.ecg_project.ui.BaseActivity
import com.alex.ecg_project.ui.list_screen.ListActivity
import com.alex.ecg_project.views.ChartHelper
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), MainScreenContract.View {

  private val TAG = this@MainActivity.javaClass.simpleName

  override var layoutId = R.layout.activity_main

  private var chartHelper: ChartHelper? = null
  private var count = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setSupportActionBar(main_toolbar)
    supportActionBar?.title = "Test"
    main_toolbar.setTitleTextColor(Color.WHITE)

    val randomService = Intent(applicationContext, RandomService::class.java)
    RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        .subscribe({
          if (it) {
            mainPresenter.startService(randomService)
            mainPresenter.subscribe(this)
          }
        }, {})

    chartHelper = ChartHelper(line_chart)
    chartHelper?.init()

    when (count) {
      0 -> main_button.text = getString(R.string.load_into_memory)
      1 -> main_button.text = getString(R.string.load_into_db)
      else -> main_button.text = getString(R.string.restart)
    }

    main_button.onClick {
      when (count) {
        0 -> {
          mainPresenter.isFirstClick = true
          count++
          main_button.text = getString(R.string.load_into_db)
        }
        1 -> {
          mainPresenter.loadDataIntoRealm(true)
          count++
          main_button.text = getString(R.string.restart)
        }
        else -> {
          main_button.text = getString(R.string.load_into_memory)
          count = 0
          mainPresenter.restart()
          chartHelper?.init()
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    outState?.putLong("time", mainPresenter.takes)
    outState?.putInt("count", count)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.list_action -> startActivity<ListActivity>()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onDestroy() {
    mainPresenter.unsubscribe()
    super.onDestroy()
  }

  override fun updateChart(value: Int) {
    chartHelper?.addEntry(value)
    Log.d(TAG, "$value")
  }

  override fun onTimerTick(seconds: Long) {
    setTime(seconds)
  }

  private fun setTime(seconds: Long) {
    timer?.let {
      if (seconds != 0L)
        it.text = String.format("%02d:%02d:%02d", TimeUnit.SECONDS.toHours(seconds), TimeUnit.SECONDS.toMinutes(seconds),
            TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)))
      else it.text = ""
    }
  }

  override fun showError(throwable: Throwable) {
    Log.e(TAG, throwable.message ?: "")
  }
}
