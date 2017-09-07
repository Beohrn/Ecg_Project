package com.alex.ecg_project.ui.list_screen

import com.alex.ecg_project.data.RealmRepository
import com.alex.ecg_project.toMainThread
import rx.Observable
import rx.Subscription

class ListScreenPresenter(val repository: RealmRepository): ListScreenContract.Presenter {

  private var subscription: Subscription? = null
  private var view: ListScreenContract.View? = null

  override fun removeData() {
    subscription = Observable.just(repository.clear())
        .toMainThread()
        .subscribe({ view?.showData(listOf()) }, { view?.showError(it) })
  }

  override fun subscribe(view: ListScreenContract.View) {
    this.view = view
    subscription = repository.getAll()
        .subscribe({ view.showData(it) }, { view.showError(it) })
  }

  override fun unsubscribe() {
    subscription?.let { if (it.isUnsubscribed) it.unsubscribe() }
  }
}