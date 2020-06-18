package com.ndn.aarchitecture.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import com.ndn.aarchitecture.widgets.dialogManager.DialogAlert
import com.ndn.aarchitecture.widgets.dialogManager.DialogConfirm
import com.ndn.aarchitecture.widgets.dialogManager.DialogManager
import com.ndn.aarchitecture.widgets.dialogManager.DialogManagerImpl
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel>(clazz: KClass<VM>) : Fragment(), BaseView {

    protected abstract val layoutID: Int
    protected val viewModel: VM by viewModel(clazz)

    private lateinit var dialogManager: DialogManager
    private val compositeDisposable = CompositeDisposable()

    protected abstract fun initialize()
    protected abstract fun onSubscribeObserver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        onSubscribeObserver()
        dialogManager = DialogManagerImpl(context)
    }

    override fun onDestroyView() {
        dialogManager.onRelease()
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) showLoading() else hideLoading()
    }

    override fun showLoading() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showLoading()
    }

    override fun hideLoading() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).hideLoading()
    }

    override fun handleApiError(apiError: Throwable) {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).handleApiError(apiError)
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
}
