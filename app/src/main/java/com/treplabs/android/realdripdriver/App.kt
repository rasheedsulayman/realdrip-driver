package com.treplabs.android.realdripdriver

import android.app.Application
import com.treplabs.android.realdripdriver.di.AppComponent
import com.treplabs.android.realdripdriver.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .application(this)
            .build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
