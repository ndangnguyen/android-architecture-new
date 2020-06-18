package com.ndn.aarchitecture.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import com.ndn.aarchitecture.utils.SingleLiveData

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val isLoading = SingleLiveData<Boolean>()
    val onError = SingleLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun launchRx(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    fun setError(error: Throwable) {
        onError.value = error
    }
}
