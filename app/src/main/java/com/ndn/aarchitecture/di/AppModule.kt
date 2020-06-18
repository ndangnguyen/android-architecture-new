package com.ndn.aarchitecture.di

import android.app.Application
import android.content.res.Resources
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ndn.aarchitecture.data.local.SharedPrefs
import com.ndn.aarchitecture.data.local.SharedPrefsImpl
import com.ndn.aarchitecture.data.remote.middleware.BooleanAdapter
import com.ndn.aarchitecture.data.remote.middleware.DoubleAdapter
import com.ndn.aarchitecture.data.remote.middleware.IntegerAdapter
import com.ndn.aarchitecture.utils.schedulerprovider.SchedulerProvider
import com.ndn.aarchitecture.utils.schedulerprovider.SchedulerProviderImp
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { provideResources(androidApplication()) }
    single { provideSharedPrefsApi(androidApplication()) }
    single { provideBaseSchedulerProvider() }
    single { provideGson() }
}

fun provideResources(app: Application): Resources {
    return app.resources
}

fun provideSharedPrefsApi(app: Application): SharedPrefs {
    return SharedPrefsImpl(app)
}

fun provideBaseSchedulerProvider(): SchedulerProvider {
    return SchedulerProviderImp()
}

fun provideGson(): Gson {
    val booleanAdapter = BooleanAdapter()
    val integerAdapter = IntegerAdapter()
    val doubleAdapter = DoubleAdapter()
    return GsonBuilder()
        .registerTypeAdapter(Boolean::class.java, booleanAdapter)
        .registerTypeAdapter(Int::class.java, integerAdapter)
        .registerTypeAdapter(Double::class.java, doubleAdapter)
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}
