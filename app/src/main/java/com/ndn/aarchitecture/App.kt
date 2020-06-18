package com.ndn.aarchitecture

import android.app.Application
import com.bumptech.glide.Glide
import com.ndn.aarchitecture.di.appModule
import com.ndn.aarchitecture.di.networkModule
import com.ndn.aarchitecture.di.repositoryModule
import com.ndn.aarchitecture.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
        configTimber()
    }

    private fun configKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, networkModule, repositoryModule, viewModelModule))
        }
    }

    private fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onLowMemory() {
        Glide.get(this).onLowMemory()
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        Glide.get(this).onTrimMemory(level)
        super.onTrimMemory(level)
    }
}
