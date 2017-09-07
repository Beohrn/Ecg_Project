package com.alex.ecg_project.managers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import pl.charmas.android.reactivelocation.ReactiveLocationProvider

class LocationManager(val context: Context) {

  private val rxLocation = ReactiveLocationProvider(context)

  @SuppressLint("MissingPermission")
  fun getAddress() = rxLocation.lastKnownLocation.flatMap { getAddressFromLocation(it) }.first()
  fun getAddressFromLocation(location: Location) = rxLocation.getReverseGeocodeObservable(location.latitude, location.longitude, Integer.MAX_VALUE).first()

}