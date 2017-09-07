package com.alex.ecg_project.ui.main_screen

import android.content.Intent
import com.alex.ecg_project.BasePresenter
import com.alex.ecg_project.BaseView

class MainScreenContract {

  interface View: BaseView {
    fun updateChart(value: Int)
    fun showError(throwable: Throwable)
    fun onTimerTick(seconds: Long)
  }

  interface Presenter: BasePresenter<View> {
    fun savePoint(point: Int)
    fun saveData()
    fun startService(intent: Intent)
    fun restart()
  }
}