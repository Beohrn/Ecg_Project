package com.alex.ecg_project

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

const val GENERATED_VALUE = "generated_value"
const val RANDOM_SERVICE = "random_service"
const val RANDOM_ACTION = "random_action"
const val ERROR_ACTION = "error_action"
const val ERROR_VALUE = "error"

const val MIN_VALUE = 0
const val MAX_VALUE = 2048

fun ClosedRange<Int>.random() = Random().nextInt(this.endInclusive - this.start) + this.start

fun <T> Observable<T>.toMainThread() = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())