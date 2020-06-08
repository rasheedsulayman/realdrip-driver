package com.treplabs.android.realdripdriver.di

import android.app.Application
import com.treplabs.android.realdripdriver.realdripapp.screens.createtreatment.ManageTreatmentFragment
import com.treplabs.android.realdripdriver.realdripapp.screens.login.LoginFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: LoginFragment)
    fun inject(target: ManageTreatmentFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
