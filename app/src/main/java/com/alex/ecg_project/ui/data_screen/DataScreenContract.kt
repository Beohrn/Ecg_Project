package com.alex.ecg_project.ui.data_screen

import com.alex.ecg_project.BasePresenter
import com.alex.ecg_project.BaseView
import com.alex.ecg_project.models.Points

/**
 * Created by aexander on 06.09.17.
 */
class DataScreenContract {

  interface View: BaseView {
    fun showData(data: Points)
    fun showError(throwable: Throwable)
  }

  interface Presenter: BasePresenter<View> {
    fun loadData(date: Long)
  }
}