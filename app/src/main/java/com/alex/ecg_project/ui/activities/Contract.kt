package com.alex.ecg_project.ui.activities

interface Contract {

  interface MainActivityView {
    fun showData(value: Int)
    fun showError(throwable: Throwable)
  }

  interface MainActivityPresenter {
    fun subscribe(view: MainActivityView)
    fun unsubscribe()
  }
}