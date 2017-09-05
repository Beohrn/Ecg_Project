package com.alex.ecg_project.config;

import android.content.Context;

import com.alex.ecg_project.presenters.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  AppPreferences provideSharedPreferences(Context context) {
    return new AppPreferences(context);
  }

  @Provides
  @Singleton
  MainActivityPresenter provideMainActivityPresenter(Context context) {
    return new MainActivityPresenter(context);
  }
}
