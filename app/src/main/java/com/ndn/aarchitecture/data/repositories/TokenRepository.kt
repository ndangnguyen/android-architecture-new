package com.ndn.aarchitecture.data.repositories

import com.ndn.aarchitecture.data.local.SharedPrefs
import com.ndn.aarchitecture.data.local.SharedPrefsKey
import com.ndn.aarchitecture.model.Token

interface TokenRepository {
    fun getToken(): Token?
    fun saveToken(token: Token)
    fun clearSharedPref()
    fun isHasLogIn(): Boolean
    fun doLogout()
}

class TokenRepositoryImpl(private val sharedPrefsApi: SharedPrefs) : TokenRepository {

    override fun getToken(): Token? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, Token::class.java)
    }

    override fun saveToken(token: Token) {
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, token)
    }

    override fun clearSharedPref() {
        sharedPrefsApi.clear()
    }

    override fun isHasLogIn(): Boolean {
        return !getToken()?.accessToken.isNullOrEmpty()
    }

    override fun doLogout() {
        clearSharedPref()
    }
}
