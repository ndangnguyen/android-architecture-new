package com.ndn.aarchitecture.data.remote.middleware

import androidx.annotation.NonNull
import com.google.gson.JsonSyntaxException
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import com.ndn.aarchitecture.data.remote.error.ErrorResponse
import com.ndn.aarchitecture.data.remote.error.RetrofitException
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber

/**
 * ErrorHandling:
 * http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 * This class only for Call in retrofit 2
 */

@Suppress("UNCHECKED_CAST")
class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {

    private val original: RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        return RxCallAdapterWrapper(
                returnType,
                wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>
        )
    }

    /**
     * RxCallAdapterWrapper
     */
    internal inner class RxCallAdapterWrapper<R>(
        private val returnType: Type,
        private val wrapped: CallAdapter<R, Any>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(@NonNull call: Call<R>): Any? {
            val rawType = getRawType(returnType)

            val isFlowable = rawType == Flowable::class.java
            val isSingle = rawType == Single::class.java
            val isMaybe = rawType == Maybe::class.java
            val isCompletable = rawType == Completable::class.java

            if (rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
                return null
            }
            if (returnType !is ParameterizedType) {
                val name = when {
                    isFlowable -> "Flowable"
                    isSingle -> "Single"
                    isMaybe -> "Maybe"
                    else -> "Observable"
                }
                throw IllegalStateException(
                        name +
                                " return type must be parameterized" +
                                " as " +
                                name +
                                "<Foo> or " +
                                name +
                                "<? extends Foo>"
                )
            }

            if (isFlowable) {
                return (wrapped.adapt(
                        call) as Flowable<*>).onErrorResumeNext { throwable: Throwable ->
                    Flowable.error(convertToBaseException(throwable))
                }
            }
            if (isSingle) {
                return (wrapped.adapt(call) as Single<*>).onErrorResumeNext { throwable ->
                    Single.error(convertToBaseException(throwable))
                }
            }
            if (isMaybe) {
                return (wrapped.adapt(call) as Maybe<*>).onErrorResumeNext { throwable: Throwable ->
                    Maybe.error(convertToBaseException(throwable))
                }
            }
            if (isCompletable) {
                return (wrapped.adapt(call) as Completable).onErrorResumeNext { throwable ->
                    Completable.error(convertToBaseException(throwable))
                }
            }
            return (wrapped.adapt(
                    call) as Observable<*>).onErrorResumeNext { throwable: Throwable ->
                Observable.error(convertToBaseException(throwable))
            }
        }

        private fun convertToBaseException(throwable: Throwable): RetrofitException {
            if (throwable is RetrofitException) {
                return throwable
            }

            // A network error happened
            if (throwable is IOException) {
                return RetrofitException.toNetworkError(throwable)
            }

            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                val errorResponse = ErrorResponse(throwable.response())
                return if (response?.errorBody() != null) {
                    try {
                        if (errorResponse.message?.isNotBlank() == true) {
                            RetrofitException.toServerError(errorResponse)
                        } else {
                            RetrofitException.toHttpError(errorResponse)
                        }
                    } catch (e: JsonSyntaxException) {
                        Timber.e(TAG, e.message)
                        RetrofitException.toUnexpectedError(e)
                    }
                } else {
                    RetrofitException.toHttpError(errorResponse)
                }
            }
            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.toUnexpectedError(throwable)
        }
    }

    companion object {

        private val TAG = RxErrorHandlingCallAdapterFactory::class.java.name

        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }
}