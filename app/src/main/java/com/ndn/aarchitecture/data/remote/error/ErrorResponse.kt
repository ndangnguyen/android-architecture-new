package com.ndn.aarchitecture.data.remote.error

import java.io.IOException
import com.ndn.aarchitecture.extension.nullToEmpty
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

data class ErrorResponse(private val response: Response<*>?) {

    var code: Int = 0

    var message: String? = null

    init {
        response?.let {
            code = it.code()
            Timber.e("errorCode: $code")
            try {
                val errorJson = JSONObject(it.errorBody()?.string().nullToEmpty()).toString()
                val errorMsg = JSONObject(errorJson).getString(ERROR_MESSAGE_PARAM)
                message = errorMsg
                Timber.e("errorMsg: $errorMsg")
            } catch (e: JSONException) {
                Timber.e(e.localizedMessage)
            } catch (e: IOException) {
                Timber.e(e.localizedMessage)
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE_PARAM = "message"
    }
}
