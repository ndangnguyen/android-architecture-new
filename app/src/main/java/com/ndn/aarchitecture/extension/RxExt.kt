package com.ndn.aarchitecture.extension

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import com.ndn.aarchitecture.utils.schedulerprovider.SchedulerProvider

/**
 * Use SchedulerProvider configuration for Completable
 */
fun Completable.async(scheduler: SchedulerProvider): Completable =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.async(scheduler: SchedulerProvider): Single<T> =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

/**
 * Use SchedulerProvider configuration for Observable
 */
fun <T> Observable<T>.async(scheduler: SchedulerProvider): Observable<T> =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

/**
 * Use SchedulerProvider configuration for Flowable
 */
fun <T> Flowable<T>.async(scheduler: SchedulerProvider): Flowable<T> =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

fun <T> Single<T>.loading(liveData: MutableLiveData<Boolean>) =
    doOnSubscribe { liveData.postValue(true) }.doAfterTerminate { liveData.postValue(false) }

fun Completable.loading(liveData: MutableLiveData<Boolean>) =
    doOnSubscribe { liveData.postValue(true) }.doAfterTerminate { liveData.postValue(false) }
