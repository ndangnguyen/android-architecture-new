package com.ndn.aarchitecture.extension

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ndn.aarchitecture.R
import com.ndn.aarchitecture.common.consts.Constants
import kotlin.reflect.KClass

/**
 * Various extension functions for AppCompatActivity.
 */

fun <T : Activity> FragmentActivity.startActivity(
    cls: KClass<T>,
    bundle: Bundle? = null,
    parcel: Parcelable? = null
) {
    intent = Intent(this, cls.java)
    if (bundle != null) intent.putExtra(Constants.EXTRA_ARGS, bundle)
    if (parcel != null) intent.putExtra(Constants.EXTRA_ARGS, parcel)
    startActivity(intent)
}

fun FragmentActivity.startActivityForResult(
    @NonNull intent: Intent,
    requestCode: Int,
    flags: Int? = null
) {
    flags?.let {
        intent.flags = it
    }
    startActivityForResult(intent, requestCode)
}

fun FragmentActivity.startActivityAtRoot(
    @NonNull clazz: KClass<out Activity>,
    args: Bundle? = null
) {
    val intent = Intent(this, clazz.java)
    args?.let {
        intent.putExtras(it)
    }
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}

fun FragmentActivity.replaceFragmentToActivity(
    @IdRes containerId: Int = R.id.flMainContainer,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    animateType: Int = Constants.AnimationType.SLIDE_TO_LEFT
) {
    supportFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }, animateType = animateType)
}

fun FragmentActivity.addFragmentToActivity(
    @IdRes containerId: Int = R.id.flMainContainer,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    animateType: Int = Constants.AnimationType.SLIDE_TO_LEFT
) {
    supportFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }, animateType = animateType)
}

fun FragmentActivity.goBackFragment(): Boolean {
    val isShowPreviousPage = supportFragmentManager.backStackEntryCount > 0
    if (isShowPreviousPage) {
        supportFragmentManager.popBackStackImmediate()
    }
    return isShowPreviousPage
}

fun FragmentActivity.isVisibleFragment(tag: String): Boolean? {
    val fragment = supportFragmentManager.findFragmentByTag(tag)
    return fragment?.isAdded ?: false && fragment?.isVisible ?: false
}

inline fun <reified T : Any> FragmentActivity.getFragmentInActivity(clazz: KClass<T>): T? {
    val tag = clazz.java.simpleName
    return supportFragmentManager.findFragmentByTag(tag) as T?
}

/**
 * val test = extra<String>("test")
 * */
inline fun <reified T : Any> FragmentActivity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

fun FragmentActivity.getCurrentFragment(@IdRes containerId: Int = R.id.flMainContainer): Fragment? {
    return supportFragmentManager.findFragmentById(containerId)
}

fun FragmentActivity.setStatusBarColor(@ColorRes color: Int, isDarkColor: Boolean = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window?.apply {
            decorView.systemUiVisibility = if (isDarkColor) Constants.DEFAULT_INT else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = ContextCompat.getColor(context, color)
        }
    }
}

fun FragmentActivity.setStatusBarColorInOut(@ColorRes colorIn: Int, @ColorRes colorOut: Int, isDarkColorInt: Boolean = false, isDarkColorOut: Boolean = false) {
    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun doWhenIn() {
            setStatusBarColor(color = colorIn, isDarkColor = isDarkColorInt)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun doWhenOut() {
            setStatusBarColor(color = colorOut, isDarkColor = isDarkColorOut)
        }
    })
}

fun FragmentActivity.setTransparentStatusBar(isDarkBackground: Boolean = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = if (isDarkBackground)
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        else
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
