package com.treplabs.android.realdripdriver.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.treplabs.android.realdripdriver.BuildConfig
import com.treplabs.android.realdripdriver.realdripdriverapp.apis.NotificationService
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [LocalDataModule::class])
class APIServiceModule {


    @Provides
    @Singleton
    fun provideNotificationService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): NotificationService {
        return Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NotificationService::class.java)
    }


    @Provides
    @Singleton
    fun provideGenericOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
