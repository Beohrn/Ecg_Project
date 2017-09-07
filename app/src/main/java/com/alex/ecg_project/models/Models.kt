package com.alex.ecg_project.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Point(open var point: Int = 0) : RealmObject()

@RealmClass
open class Points : RealmObject() {
  open var points = RealmList<Point>()
  open var date: Long = 0
  open var address = ""
}