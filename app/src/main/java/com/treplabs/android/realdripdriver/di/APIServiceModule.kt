package com.treplabs.android.realdripdriver.di

import com.treplabs.android.realdripdriver.BuildConfig
import com.treplabs.android.realdripdriver.auth.AccessTokenAuthenticator
import com.treplabs.android.realdripdriver.auth.AccessTokenInterceptor
import com.treplabs.android.realdripdriver.auth.AccessTokenProvider
import com.treplabs.android.realdripdriver.realdripapp.accesstoken.AccessTokenProviderImpl
import com.treplabs.android.realdripdriver.realdripapp.apis.RealDripAPIAuthService
import com.treplabs.android.realdripdriver.realdripapp.apis.RealDripApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [LocalDataModule::class])
class APIServiceModule {

    @Provides
    @Named("RealDripAPIService")
    @Singleton
    fun provideRealDripServiceHttpClient(
        upstream: OkHttpClient,
        @Named("RealDripAPIService") accessTokenProvider: AccessTokenProvider
    ): OkHttpClient {
        return upstream.newBuilder()
            .addInterceptor(AccessTokenInterceptor(accessTokenProvider))
            .authenticator(AccessTokenAuthenticator(accessTokenProvider))
            .build()
    }

    @Provides
    @Singleton
    fun provideRealDripAPIAuthService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): RealDripAPIAuthService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RealDripAPIAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideRealDripAPIService(
        @Named("RealDripAPIService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): RealDripApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RealDripApiService::class.java)
    }

    @Provides
    @Named("RealDripAPIService")
    fun provideAccessTokenProvider(accessTokenProvider: AccessTokenProviderImpl): AccessTokenProvider =
        accessTokenProvider

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
