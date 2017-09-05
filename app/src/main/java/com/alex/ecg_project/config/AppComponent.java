package com.alex.ecg_project.config;

import com.alex.ecg_project.ui.BaseActivity;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = { AppModule.class })
public interface AppComponent {
  void inject(BaseActivity baseActivity);
}

