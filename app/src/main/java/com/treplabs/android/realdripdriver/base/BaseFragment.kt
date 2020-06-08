package com.treplabs.android.realdripdriver.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.treplabs.android.realdripdriver.App
import com.treplabs.android.realdripdriver.R
import com.treplabs.android.realdripdriver.di.AppComponent
import com.treplabs.android.realdripdriver.MainActivity

abstract class BaseFragment : Fragment(), LoadingCallback {

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

    protected val daggerAppComponent: AppComponent
        get() = (mainActivity.applicationContext as App).component

    override fun onStart() {
        super.onStart()
        mainActivity.setCurrentFragment(this)
    }

    fun showMessageDialog(message: String) = mainActivity.showMessageDialog(message)


    fun showDialogWithAction(
        title: String? = null,
        body: String? = null,
        @StringRes positiveRes: Int = R.string.ok,
        positiveAction: (() -> Unit)? = null,
        @StringRes negativeRes: Int? = null,
        negativeAction: (() -> Unit)? = null
    ) = mainActivity.showDialogWithAction(
        title,
        body,
        positiveRes,
        positiveAction,
        negativeRes,
        negativeAction
    )

    fun showSnackBar(@StringRes stringRes: Int) = mainActivity.showSnackBar(getString(stringRes))

    fun showSnackBar(message: String) = mainActivity.showSnackBar(message)

    // Return true if you handle the back press in your fragment
    open fun onBackPressed(): Boolean = false

    override fun showLoading() = mainActivity.showLoading()

    override fun showLoading(resId: Int) = mainActivity.showLoading(resId)

    override fun showLoading(message: String) = mainActivity.showLoading(message)

    override fun dismissLoading() = mainActivity.dismissLoading()

    override fun showError(resId: Int) = mainActivity.showError(resId)

    override fun showError(message: String) = mainActivity.showError(message)

    override fun isLoading(): Boolean = mainActivity.isLoading()
}
