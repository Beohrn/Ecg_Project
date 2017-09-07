package com.alex.ecg_project

interface BaseView

interface BasePresenter<in V: BaseView> {
  fun subscribe(view: V)
  fun unsubscribe()
}