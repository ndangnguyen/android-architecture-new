package com.ndn.aarchitecture.widgets.dialogManager

import com.ndn.aarchitecture.widgets.dialogManager.DialogAlert
import com.ndn.aarchitecture.widgets.dialogManager.DialogConfirm

interface DialogManager {

    fun showLoading()

    fun showProcessing()

    fun hideLoading()

    fun onRelease()

    fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        listener: DialogAlert.OnButtonClickedListener?
    )

    fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        buttonBgColor: Int,
        buttonColor: Int,
        listener: DialogAlert.OnButtonClickedListener?
    )

    fun showConfirmDialog(
        title: String?,
        message: String?,
        titleButtonPositive: String,
        titleButtonNegative: String,
        isPayment: Boolean?,
        listener: DialogConfirm.OnButtonClickedListener?
    )
}
