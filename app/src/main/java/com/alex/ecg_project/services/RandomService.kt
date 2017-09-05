package com.alex.ecg_project.services

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.alex.ecg_project.*
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RandomService : IntentService(RANDOM_SERVICE) {

  private var subscription: Subscription? = null

  override fun onDestroy() {
    super.onDestroy()
    subscription?.let {
      if (it.isUnsubscribed)
        it.unsubscribe()
    }
  }

  override fun onHandleIntent(intent: Intent?) {

  }

  override fun onCreate() {
    super.onCreate()
    subscription = Observable.interval(20, TimeUnit.MILLISECONDS, Schedulers.computation())
        .subscribe({ sendIntent(generate()) }, { sendError(it.message!!)})
  }

  private fun generate() = (MIN_VALUE..MAX_VALUE).random()

  private fun sendIntent(value: Int) {
    val intent = Intent(RANDOM_ACTION)
    intent.putExtra(GENERATED_VALUE, value)
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
  }

  private fun sendError(error: String) {
    val intent = Intent(ERROR_ACTION)
    intent.putExtra(ERROR_VALUE, error)
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
  }
}