package com.alex.ecg_project.presenters

import android.content.Context
import com.alex.ecg_project.MAX_VALUE
import com.alex.ecg_project.MIN_VALUE
import com.alex.ecg_project.random
import com.alex.ecg_project.ui.activities.Contract
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivityPresenter(val context: Context): Contract.MainActivityPresenter {

  private var subscription: Subscription? = null

  override fun subscribe(view: Contract.MainActivityView) {
    subscription = Observable.interval(20, TimeUnit.MILLISECONDS, Schedulers.computation())
        .subscribe({
          view.showData(generate())
        }, {
          view.showError(it)
        })
  }

  override fun unsubscribe() {
    subscription?.let {
      if (it.isUnsubscribed)
        it.unsubscribe()
    }
  }

  private fun generate() = (MIN_VALUE..MAX_VALUE).random()
}