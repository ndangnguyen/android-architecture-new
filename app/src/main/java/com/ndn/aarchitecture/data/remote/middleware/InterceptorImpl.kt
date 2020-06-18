package com.ndn.aarchitecture.data.remote.middleware

import androidx.annotation.NonNull
import com.ndn.aarchitecture.BuildConfig
import com.ndn.aarchitecture.data.repositories.TokenRepository
import java.io.IOException
import java.net.HttpURLConnection
import com.ndn.aarchitecture.extension.insert
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class InterceptorImpl(private var tokenRepository: TokenRepository?) : Interceptor {

    companion object {
        private const val TAG = "InterceptorImpl"
        private const val TOKEN_TYPE = "Bearer "
        private const val KEY_TOKEN = "Authorization"
    }

    private var mIsRefreshToken: Boolean = false

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {

        val builder = initializeHeader(chain)
        var request = builder.build()
        var response = chain.proceed(request)

        if (!mIsRefreshToken && response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {

            builder.removeHeader(
                KEY_TOKEN
            )

            tokenRepository?.getToken()?.accessToken?.let { accessToken ->
                builder.addHeader(
                    KEY_TOKEN, TOKEN_TYPE + accessToken
                )
                request = builder.build()
                response = chain.proceed(request)
            }
        }

        return response
    }

    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val originRequest = chain.request()

        val urlWithPrefix = BuildConfig.END_POINT
        val newUrl = chain.request().url.toString().insert(
            index = urlWithPrefix.length,
            contentInsert = ""
        )
        Timber.e("newUrl $newUrl")
        val builder = originRequest.newBuilder()
            .url(newUrl)
            .header("Accept", "application/json")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Cache-Control", "no-store")
            .method(originRequest.method, originRequest.body)

        tokenRepository?.getToken()?.accessToken?.let { accessToken ->
            builder.addHeader(KEY_TOKEN, TOKEN_TYPE + accessToken)
        }

        return builder
    }
}
