package com.treplabs.android.realdripdriver.di

import android.app.Application
import com.treplabs.android.realdripdriver.realdripdriverapp.screens.infusiondetails.InfusionDetailsFragment
import com.treplabs.android.realdripdriver.realdripdriverapp.screens.devicedetails.DeviceDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: DeviceDetailsFragment)
    fun inject(target: InfusionDetailsFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
