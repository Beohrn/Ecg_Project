package com.alex.ecg_project.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alex.ecg_project.EcgApplication
import com.alex.ecg_project.presenters.MainActivityPresenter
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

  @Inject
  protected lateinit var presenter: MainActivityPresenter

  protected abstract var layoutId: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    EcgApplication.getComponent().inject(this)
    setContentView(layoutId)
  }
}