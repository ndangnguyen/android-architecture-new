package com.ndn.aarchitecture.di

import android.app.Application
import com.ndn.aarchitecture.data.local.SharedPrefs
import com.ndn.aarchitecture.data.local.SharedPrefsImpl
import com.ndn.aarchitecture.data.remote.Api
import com.ndn.aarchitecture.data.repositories.AppRepository
import com.ndn.aarchitecture.data.repositories.AppRepositoryImpl
import com.ndn.aarchitecture.data.repositories.TokenRepository
import com.ndn.aarchitecture.data.repositories.TokenRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val repositoryModule = module {
    single { provideTokenRepository(androidApplication()) }
    single { provideRepository(get(), get()) }
}

fun provideTokenRepository(app: Application): TokenRepository {
    return TokenRepositoryImpl(SharedPrefsImpl(app))
}

fun provideRepository(api: Api, sharedPrefs: SharedPrefs): AppRepository =
    AppRepositoryImpl(api, sharedPrefs)
