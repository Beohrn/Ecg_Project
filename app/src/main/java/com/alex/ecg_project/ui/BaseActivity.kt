package com.alex.ecg_project.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.alex.ecg_project.EcgApplication
import com.alex.ecg_project.ui.data_screen.DataScreenPresenter
import com.alex.ecg_project.ui.list_screen.ListScreenPresenter
import com.alex.ecg_project.ui.main_screen.MainScreenPresenter
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

  @Inject
  lateinit var mainPresenter: MainScreenPresenter

  @Inject
  lateinit var listPresenter: ListScreenPresenter

  @Inject
  lateinit var dataPresenter: DataScreenPresenter

  protected abstract var layoutId: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    EcgApplication.getComponent().inject(this)
    setContentView(layoutId)
  }
}