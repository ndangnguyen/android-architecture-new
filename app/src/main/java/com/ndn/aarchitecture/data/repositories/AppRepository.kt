package com.ndn.aarchitecture.data.repositories

import com.ndn.aarchitecture.data.local.SharedPrefs
import com.ndn.aarchitecture.data.remote.Api

interface AppRepository {
}

class AppRepositoryImpl(
    private val api: Api,
    private val sharedPrefs: SharedPrefs
) : AppRepository {

}
