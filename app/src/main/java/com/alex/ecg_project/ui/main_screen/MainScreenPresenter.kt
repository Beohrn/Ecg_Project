package com.alex.ecg_project.ui.main_screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.alex.ecg_project.ERROR_VALUE
import com.alex.ecg_project.GENERATED_VALUE
import com.alex.ecg_project.RANDOM_ACTION
import com.alex.ecg_project.data.RealmRepository
import com.alex.ecg_project.managers.LocationManager
import com.alex.ecg_project.models.Point
import com.alex.ecg_project.models.Points
import com.alex.ecg_project.toMainThread
import rx.Observable
import rx.Subscription
import java.util.concurrent.TimeUnit

class MainScreenPresenter(val context: Context, val repository: RealmRepository,
                          val locationManager: LocationManager) : MainScreenContract.Presenter {

  private var intent: Intent? = null
  private var view: MainScreenContract.View? = null
  private var points = arrayListOf<Point>()
  private var timer: Subscription? = null
  var takes = 0L
  var isFirstClick = false
  var isSecondClick = false

  override fun savePoint(point: Int) {
    view?.updateChart(point)
    if (isFirstClick) points.add(Point(point))
  }

  fun loadDataIntoRealm(value: Boolean) {
    isSecondClick = value
    unsubscribe()
    saveData()
  }


  override fun saveData() {
    if (points.isNotEmpty()) {
      val item = Points()
      item.points.addAll(points)
      item.date = System.currentTimeMillis()
      addLocation(item)
    }
  }

  private fun addLocation(item: Points) {
    locationManager.getAddress().subscribe({
      item.address = it.first().getAddressLine(0)
      repository.add(item)
      points.clear()
    }, {
      view?.showError(it)
    })
  }

  override fun restart() {
    takes
    isFirstClick = false
    isSecondClick = false
    intent?.let { startService(it) }
    view?.let { subscribe(it) }
  }

  override fun startService(intent: Intent) {
    if (this.intent == null) {
      this.intent = intent
      context.startService(intent)
    }
  }

  private fun startTimer() {
    timer = Observable.interval(1, TimeUnit.SECONDS)
        .toMainThread()
        .subscribe({
          takes++
          view?.onTimerTick(takes)
        }, { view?.showError(it) })
  }

  override fun subscribe(view: MainScreenContract.View) {
    this.view = view
    if (!isSecondClick) startTimer()
    val intentFilter = IntentFilter()
    intentFilter.addAction(RANDOM_ACTION)
    LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter)

  }

  override fun unsubscribe() {
    timer?.unsubscribe()
    context.stopService(intent)
    LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
  }

  private val broadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      intent?.let {
        if (it.action == RANDOM_ACTION)
          if (!isSecondClick) {
            savePoint(it.getIntExtra(GENERATED_VALUE, -1))
          }
        else view?.showError(Throwable(it.getStringExtra(ERROR_VALUE)))

      }
    }
  }
}