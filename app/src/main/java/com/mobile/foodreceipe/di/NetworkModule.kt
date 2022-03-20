package com.mobile.foodreceipe.di

import android.content.Context
import com.mobile.foodreceipe.interfaces.FoodReceipeAPI
import com.mobile.foodreceipe.singletons.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient():OkHttpClient{
        return OkHttpClient.Builder().readTimeout(15,TimeUnit.SECONDS).connectTimeout(15,TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideGsonConvertorFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(gsonConverterFactory: GsonConverterFactory,okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(gsonConverterFactory).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideReceipeAPI(retrofit: Retrofit):FoodReceipeAPI{
        return retrofit.create(FoodReceipeAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context):Context{
        return context
    }


}