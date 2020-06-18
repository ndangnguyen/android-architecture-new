package com.ndn.aarchitecture.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.ndn.aarchitecture.common.consts.Constants.DEFAULT_BOOLEAN
import com.ndn.aarchitecture.common.consts.Constants.DEFAULT_FLOAT
import com.ndn.aarchitecture.common.consts.Constants.DEFAULT_INT
import com.ndn.aarchitecture.common.consts.Constants.DEFAULT_LONG
import com.ndn.aarchitecture.common.consts.Constants.DEFAULT_STRING

@Suppress("UNCHECKED_CAST")
class SharedPrefsImpl(context: Context) : SharedPrefs {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            SharedPrefsKey.PREF_NAME, Context.MODE_PRIVATE
        )

    override fun <T> get(key: String, clazz: Class<T>): T {
        return when (clazz) {
            String::class.java -> sharedPreferences.getString(key, DEFAULT_STRING).let { it as T }
            Boolean::class.java -> sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN).let { it as T }
            Float::class.java -> sharedPreferences.getFloat(key, DEFAULT_FLOAT).let { it as T }
            Int::class.java -> sharedPreferences.getInt(key, DEFAULT_INT).let { it as T }
            Long::class.java -> sharedPreferences.getLong(key, DEFAULT_LONG).let { it as T }
            else -> Gson().fromJson(sharedPreferences.getString(key, DEFAULT_STRING), clazz)
        }
    }

    override fun <T> put(key: String, data: T) {
        sharedPreferences.edit {
            when (data) {
                is String -> putString(key, data)
                is Boolean -> putBoolean(key, data)
                is Float -> putFloat(key, data)
                is Int -> putInt(key, data)
                is Long -> putLong(key, data)
                else -> putString(key, Gson().toJson(data))
            }
        }
    }

    override fun clear() {
        sharedPreferences.edit()?.clear()?.apply()
    }

    override fun clearKey(key: String) {
        sharedPreferences.edit()?.remove(key)?.apply()
    }
}
