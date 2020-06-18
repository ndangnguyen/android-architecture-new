package com.ndn.aarchitecture.common.base

import com.ndn.aarchitecture.widgets.dialogManager.DialogAlert
import com.ndn.aarchitecture.widgets.dialogManager.DialogConfirm
import io.reactivex.rxjava3.disposables.Disposable

interface BaseView {

    fun showLoading(isShow: Boolean)
    fun showLoading()
    fun hideLoading()
    fun handleApiError(apiError: Throwable)
    fun launchRx(job: () -> Disposable)

    fun showAlertDialog(
        title: String = "",
        message: String = "",
        titleButton: String = "",
        listener: DialogAlert.OnButtonClickedListener? = null
    )

    fun showConfirmDialog(
        title: String? = "",
        message: String? = "",
        titleButtonPositive: String = "",
        titleButtonNegative: String = "",
        listener: DialogConfirm.OnButtonClickedListener? = null,
        isPayment: Boolean? = null
    )
}
