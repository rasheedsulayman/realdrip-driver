package com.treplabs.android.realdripdriver.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.treplabs.android.realdripdriver.networkutils.LoadingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel() {

    val observablesList: MutableList<LiveData<*>> = mutableListOf()

    protected val _loadingStatus = MutableLiveData<LoadingStatus>()

    private val viewModelBackgroundJob = SupervisorJob()
    protected val viewModelIOScope = CoroutineScope(viewModelBackgroundJob + Dispatchers.IO)

    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    override fun onCleared() {
        super.onCleared()
        viewModelBackgroundJob.cancel()
    }

}
