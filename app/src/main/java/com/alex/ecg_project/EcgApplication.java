package com.alex.ecg_project;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alex.ecg_project.config.AppComponent;
import com.alex.ecg_project.config.AppModule;
import com.alex.ecg_project.config.DaggerAppComponent;
import com.facebook.stetho.Stetho;

public class EcgApplication extends Application {

  private static AppComponent component;

  public static AppComponent getComponent() {
    return component;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    Stetho.initializeWithDefaults(this);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
