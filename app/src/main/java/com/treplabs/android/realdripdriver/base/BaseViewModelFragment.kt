package com.treplabs.android.realdripdriver.base

import androidx.lifecycle.Observer
import com.treplabs.android.realdripdriver.networkutils.LoadingStatus

abstract class BaseViewModelFragment : BaseFragment() {

    override fun onStart() {
        super.onStart()
        getViewModel().loadingStatus.observe(this, Observer {
            when (it) {
                LoadingStatus.Success -> dismissLoading()
                is LoadingStatus.Loading -> showLoading(it.message)
                is LoadingStatus.Error -> {
                    showError(it.errorMessage)
                }
            }
        })
    }

    abstract fun getViewModel(): BaseViewModel
}
