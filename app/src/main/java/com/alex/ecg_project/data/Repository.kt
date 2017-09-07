package com.alex.ecg_project.data

import rx.Observable

interface Repository<T> {
  fun add(value: T)
  fun addAll(values: List<T>)
  fun getAll(): Observable<List<T>>
  fun clear()
}