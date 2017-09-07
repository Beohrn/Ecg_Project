package com.alex.ecg_project.data

import com.alex.ecg_project.managers.RealmManager
import com.alex.ecg_project.models.Points

class RealmRepository(val realmManager: RealmManager): Repository<Points> {

  override fun add(value: Points) {
    realmManager.addObject(value)
  }

  override fun addAll(values: List<Points>) {
    realmManager.addObjects(values)
  }

  fun getByField(fieldName: String, value: Long) = realmManager.findObjectByField<Points>(fieldName, value)

  override fun getAll() = realmManager.findAllObjects<Points>()

  override fun clear() = realmManager.clearDataBase()
}