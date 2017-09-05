package com.alex.ecg_project

interface BaseView {
  fun showData(value: Int)
  fun showError()
}

interface BasePresenter<in V: BaseView> {
  fun subscribe(view: V)
  fun unsubscribe()
}