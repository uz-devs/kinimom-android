package org.codventure.kinimom.framework.di

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.framework.network.Network
import retrofit2.converter.gson.GsonConverterFactory
import org.codventure.kinimom.AndroidApplication
import okhttp3.logging.HttpLoggingInterceptor
import android.content.Context
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import dagger.Provides
import dagger.Module

@Module
class ApplicationModule(private val androidApplication: AndroidApplication) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = androidApplication

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build()
        return Retrofit.Builder()
            .baseUrl("https://kinicaremom.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKinimomRepository(source: Network): KinimomRepository {
        return source
    }
}