package org.codventure.kinimom.framework.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.BuildConfig
import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.framework.network.Network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by abduaziz on 7/17/21 at 10:16 PM.
 */

@Module
class ApplicationModule(private val androidApplication: AndroidApplication) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = androidApplication

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
        return Retrofit.Builder()
            .baseUrl("https://kinicaremom.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKinimomRepository(source: Network): KinimomRepository{
        return source
    }
}