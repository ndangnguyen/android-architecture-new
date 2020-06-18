package com.ndn.aarchitecture.extension

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ndn.aarchitecture.R
import com.ndn.aarchitecture.common.consts.Constants

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    animateType: Int = Constants.AnimationType.SLIDE_TO_LEFT
) {
    childFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }, animateType = animateType)
}

fun Fragment.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    animateType: Int = Constants.AnimationType.SLIDE_TO_LEFT
) {
    childFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }, animateType = animateType)
}

fun Fragment.isCanPopBackStack(): Boolean {
    childFragmentManager.let {
        val isShowPreviousPage = it.backStackEntryCount > 0
        if (isShowPreviousPage) {
            it.popBackStackImmediate()
        }
        return isShowPreviousPage
    }
}

fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply { arguments = Bundle().apply(argsBuilder) }

/**
 * Runs a FragmentTransaction, then calls commitAllowingStateLoss().
 */
inline fun FragmentManager.transact(
    action: FragmentTransaction.() -> Unit,
    animateType: Int = Constants.AnimationType.SLIDE_TO_LEFT
) {
    beginTransaction().apply {
        setCustomAnimations(this, animateType)
        action()
    }.commitAllowingStateLoss()
}

fun setCustomAnimations(
    transaction: FragmentTransaction,
    animateType: Int = Constants.AnimationType.SLIDE_TO_LEFT
) {
    when (animateType) {
        Constants.AnimationType.FADE -> {
            transaction.setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
        }
        Constants.AnimationType.SLIDE_TO_RIGHT -> {
            transaction.setCustomAnimations(
                R.anim.exit_to_left, R.anim.enter_from_right,
                R.anim.exit_to_right, R.anim.enter_from_left
            )
        }
        Constants.AnimationType.BOTTOM_UP -> {
            transaction.setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
        }
        Constants.AnimationType.SLIDE_TO_LEFT -> {
            transaction.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right
            )
        }
    }
}

/**
 * If no window token is found, keyboard is checked using reflection to know if keyboard visibility toggle is needed
 *
 * @param useReflection - whether to use reflection in case of no window token or not
 */
fun Fragment.hideKeyboard(useReflection: Boolean = true) {
    val windowToken = view?.rootView?.windowToken
    val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    windowToken?.let {
        imm.hideSoftInputFromWindow(windowToken, 0)
    } ?: run {
        if (useReflection) {
            try {
                if (InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight").invoke(imm) as Int > 0) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            } catch (exception: Exception) {
            }
        }
    }
}

fun Fragment.setStatusBarColor(@ColorRes color: Int, isDarkColor: Boolean = false) {
    activity?.setStatusBarColor(color, isDarkColor)
}

fun Fragment.setStatusBarColorInOut(@ColorRes colorIn: Int, @ColorRes colorOut: Int, isDarkColorInt: Boolean = false, isDarkColorOut: Boolean = false) {
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

fun Fragment.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimension(resourceId).toInt()
    }
    return result
}

fun Fragment.setTransparentStatusBar(isDarkBackground: Boolean = false) {
    activity?.setTransparentStatusBar(isDarkBackground)
}

fun Fragment.setDarkStatusBarInThisFragment() {
    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun doWhenIn() {
            setTransparentStatusBar(isDarkBackground = true)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun doWhenOut() {
            setTransparentStatusBar()
        }
    })
}
