package com.ndn.aarchitecture.widgets.dialogManager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class DialogManagerImpl(ctx: Context?) : DialogManager {

    private var context: WeakReference<Context?>? = null
    private var progressDialog: ProgressDialog? = null

    init {
        context = WeakReference(ctx).apply {
            progressDialog = ProgressDialog(this.get()!!)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun showProcessing() {
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }

    override fun onRelease() {
        progressDialog = null
    }

    override fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        listener: DialogAlert.OnButtonClickedListener?
    ) {
        val dialog = DialogAlert.newInstance(
                title, message, titleButton, listener
        )
        val fm = (context?.get() as AppCompatActivity).supportFragmentManager
        dialog.show(fm, DialogAlert::class.java.simpleName)
    }

    override fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        buttonBgColor: Int,
        buttonColor: Int,
        listener: DialogAlert.OnButtonClickedListener?
    ) {
        val dialog = DialogAlert.newInstance(
                title, message, titleButton, listener
        )
        val fm = (context?.get() as AppCompatActivity).supportFragmentManager
        dialog.show(fm, DialogAlert::class.java.simpleName)
    }

    override fun showConfirmDialog(
        title: String?,
        message: String?,
        titleButtonPositive: String,
        titleButtonNegative: String,
        isPayment: Boolean?,
        listener: DialogConfirm.OnButtonClickedListener?
    ) {
        val dialog = DialogConfirm.newInstance(
                title, message, titleButtonPositive,
                titleButtonNegative, listener
        )
        val fm = (context?.get() as AppCompatActivity).supportFragmentManager
        dialog.show(fm, DialogAlert::class.java.simpleName)
    }
}
