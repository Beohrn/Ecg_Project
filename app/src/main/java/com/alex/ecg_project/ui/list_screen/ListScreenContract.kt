package com.alex.ecg_project.ui.list_screen

import com.alex.ecg_project.BasePresenter
import com.alex.ecg_project.BaseView
import com.alex.ecg_project.models.Points

class ListScreenContract {

  interface View: BaseView {
    fun showData(points: List<Points>)
    fun showError(throwable: Throwable)
  }

  interface Presenter: BasePresenter<View> {
    fun removeData()
  }
}