package com.alex.ecg_project.ui.data_screen

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.alex.ecg_project.R
import com.alex.ecg_project.getDate
import com.alex.ecg_project.models.Points
import com.alex.ecg_project.ui.BaseActivity
import com.alex.ecg_project.views.ChartHelper
import kotlinx.android.synthetic.main.activity_data.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class DataActivity: BaseActivity(), DataScreenContract.View {

  override var layoutId = R.layout.activity_data

  companion object {
    const val DATE_KEY = "date_key"
  }

  private val TAG = this@DataActivity.javaClass.simpleName
  private var chartHelper: ChartHelper? = null
  private val date: Long by lazy { intent.getLongExtra(DATE_KEY, -1) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setSupportActionBar(data_toolbar)
    with(supportActionBar!!) {
      setHomeButtonEnabled(true)
      setDisplayHomeAsUpEnabled(true)
    }

    data_toolbar.setTitleTextColor(Color.WHITE)
    data_toolbar.setNavigationOnClickListener { finish() }
    chartHelper = ChartHelper(data_line_chart)
    chartHelper?.init()
  }

  override fun onResume() {
    super.onResume()
    dataPresenter.subscribe(this)
    dataPresenter.loadData(date)
  }

  override fun showData(data: Points) {
    title = getDate(data.date)

    Observable.interval(10, TimeUnit.MICROSECONDS, AndroidSchedulers.mainThread())
        .zipWith(Observable.from(data.points)) { _, l -> l.point }
        .subscribe({ chartHelper?.addEntry(it) }, {})
  }

  override fun showError(throwable: Throwable) {
    Log.e(TAG, throwable.message ?: "")
  }
}