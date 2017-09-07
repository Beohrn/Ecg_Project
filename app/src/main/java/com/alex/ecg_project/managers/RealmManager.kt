package com.alex.ecg_project.managers

import io.realm.Realm

class RealmManager(val realm: Realm) {

  fun addObject(clazz: io.realm.RealmObject) = realm.executeTransactionAsync { it.copyToRealm(clazz) }

  fun addObjects(objects: List<io.realm.RealmObject>) = realm.executeTransactionAsync { it.copyToRealm(objects) }

  inline fun <reified E : io.realm.RealmObject> findObjectByField(fieldName: String, value: Long) =
      realm.where(E::class.java).equalTo(fieldName, value).findFirstAsync().asObservable<E>().filter { it.isValid && it.isLoaded }

  inline fun <reified E : io.realm.RealmObject> findAllObjects() = realm.where(E::class.java).findAllAsync().asObservable().map { it.toList() }

  inline fun <reified E : io.realm.RealmObject> findAllObjectsByField(fieldName: String, value: String) =
      realm.where(E::class.java).equalTo(fieldName, value).findAllAsync()

  fun clearDataBase() = realm.executeTransaction(Realm::deleteAll)
}