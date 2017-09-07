package com.alex.ecg_project.ui.data_screen

import com.alex.ecg_project.data.RealmRepository
import rx.Subscription

class DataScreenPresenter(val repository: RealmRepository): DataScreenContract.Presenter {

  private var subscription: Subscription? = null
  private var view: DataScreenContract.View? = null
  private val DATE_FIELD = "date"

  override fun loadData(date: Long) {
    subscription = repository.getByField(DATE_FIELD, date)
        .subscribe({ view?.showData(it) }, { view?.showError(it) })
  }

  override fun subscribe(view: DataScreenContract.View) {
    this.view = view
  }

  override fun unsubscribe() {
    subscription?.let {
      if (it.isUnsubscribed)
        it.unsubscribe()
    }
  }
}