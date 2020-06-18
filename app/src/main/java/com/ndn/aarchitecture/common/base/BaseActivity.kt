package com.ndn.aarchitecture.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ndn.aarchitecture.data.remote.error.RetrofitException
import com.ndn.aarchitecture.widgets.dialogManager.DialogAlert
import com.ndn.aarchitecture.widgets.dialogManager.DialogConfirm
import com.ndn.aarchitecture.widgets.dialogManager.DialogManager
import com.ndn.aarchitecture.widgets.dialogManager.DialogManagerImpl
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.HttpURLConnection
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(clazz: KClass<VM>) : AppCompatActivity(), BaseView {

    protected abstract val layoutID: Int
    protected val viewModel: VM by viewModel(clazz)

    private lateinit var dialogManager: DialogManager
    private val compositeDisposable = CompositeDisposable()
    protected abstract fun initialize()
    protected abstract fun onSubscribeObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutID)
        dialogManager = DialogManagerImpl(this)
        initialize()
        onSubscribeObserver()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        dialogManager.onRelease()
        super.onDestroy()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) showLoading() else hideLoading()
    }

    override fun showLoading() {
        dialogManager.showLoading()
    }

    override fun hideLoading() {
        dialogManager.hideLoading()
    }

    override fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        listener: DialogAlert.OnButtonClickedListener?
    ) {
        dialogManager.showAlertDialog(title, message, titleButton, listener)
    }

    override fun showConfirmDialog(
        title: String?,
        message: String?,
        titleButtonPositive: String,
        titleButtonNegative: String,
        listener: DialogConfirm.OnButtonClickedListener?,
        isPayment: Boolean?
    ) {
        dialogManager.showConfirmDialog(
            title, message, titleButtonPositive, titleButtonNegative, isPayment,
            listener
        )
    }

    override fun launchRx(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    override fun handleApiError(apiError: Throwable) {
        if (apiError is RetrofitException) {
            if (apiError.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                apiError.getMsgError()?.let {
                    showAlertDialog(message = it,
                        listener = object : DialogAlert.OnButtonClickedListener {
                            override fun onPositiveClicked() {
                            }
                        })
                }
                return
            }
            if (apiError.getErrorCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                apiError.getMsgError()?.let {
                    showAlertDialog(message = it)
                }
                return
            }
            apiError.getMsgError()?.let {
                showAlertDialog(message = it)
            }
        }
    }
}
