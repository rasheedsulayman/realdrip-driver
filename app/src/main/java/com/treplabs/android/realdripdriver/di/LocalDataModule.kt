package com.treplabs.android.realdripdriver.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.treplabs.android.realdripdriver.utils.PrefsUtils
import com.google.gson.Gson
import com.treplabs.android.realdripdriver.realdripapp.apis.RealDripApiService
import com.treplabs.android.realdripdriver.realdripapp.data.store.InfusionDatabase
import com.treplabs.android.realdripdriver.realdripapp.data.repositories.InfusionRepository
import com.treplabs.android.realdripdriver.realdripapp.data.repositories.InfusionRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun providePrefsUtils(prefs: SharedPreferences, gson: Gson): PrefsUtils =
        PrefsUtils(prefs, gson)

    @Provides
    @Singleton
    fun provideGlobalSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences("global_shared_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideInfusionDatabase(app: Application): InfusionDatabase =
        Room.databaseBuilder(app, InfusionDatabase::class.java, "infusion-database.db").build()

    @Provides
    @Singleton
    fun provideInfusionRepository(realDripApiService: RealDripApiService): InfusionRepository =
        //Todo: Swap this Local repository to the remote InfusionRepositoryImpl
        InfusionRepositoryImpl(realDripApiService)
}
