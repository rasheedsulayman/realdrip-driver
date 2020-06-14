package com.treplabs.android.realdripdriver.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.treplabs.android.realdripdriver.realdripdriverapp.screens.devicedetails.DeviceDetailsViewModel
import com.treplabs.android.realdripdriver.realdripdriverapp.screens.infusiondetails.InfusionDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: RealDripViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DeviceDetailsViewModel::class)
    abstract fun bindDeviceDetailsViewModel(viewModel: DeviceDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfusionDetailsViewModel::class)
    abstract fun bindInfusionDetails(viewModel: InfusionDetailsViewModel): ViewModel
}
