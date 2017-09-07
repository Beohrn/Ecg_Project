package com.alex.ecg_project.config;

import android.content.Context;

import com.alex.ecg_project.managers.LocationManager;
import com.alex.ecg_project.managers.RealmManager;
import com.alex.ecg_project.data.RealmRepository;
import com.alex.ecg_project.ui.data_screen.DataScreenPresenter;
import com.alex.ecg_project.ui.list_screen.ListScreenPresenter;
import com.alex.ecg_project.ui.main_screen.MainScreenPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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
  MainScreenPresenter provideMainActivityPresenter(Context context, RealmRepository repository, LocationManager locationManager) {
    return new MainScreenPresenter(context, repository, locationManager);
  }

  @Provides
  @Singleton
  Realm provideRealm(Context context) {
    Realm.init(context);
    RealmConfiguration configuration = new RealmConfiguration.Builder().build();
    Realm.setDefaultConfiguration(configuration);
    return Realm.getDefaultInstance();
  }

  @Provides
  @Singleton
  RealmManager provideRealmManager(Realm realm) {
    return new RealmManager(realm);
  }

  @Provides
  @Singleton
  RealmRepository provideRealmRepository(RealmManager realmManager) {
    return new RealmRepository(realmManager);
  }

  @Provides
  @Singleton
  LocationManager provideLocationManager(Context context) {
    return new LocationManager(context);
  }

  @Provides
  @Singleton
  ListScreenPresenter provideListScreenPresenter(RealmRepository repository) {
    return new ListScreenPresenter(repository);
  }

  @Provides
  @Singleton
  DataScreenPresenter provideDataScreenPresenter(RealmRepository repository) {
    return new DataScreenPresenter(repository);
  }
}
