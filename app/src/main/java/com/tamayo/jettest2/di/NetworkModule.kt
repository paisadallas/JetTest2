package com.tamayo.jettest2.di

import com.google.gson.Gson
import com.tamayo.jettest2.data.rest.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient, gson: Gson
    ): Retrofit = Retrofit.Builder().baseUrl(ServiceApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesServiceApi(retrofit: Retrofit): ServiceApi =
        retrofit.create(ServiceApi::class.java)


    @Singleton
    @Provides
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO


}