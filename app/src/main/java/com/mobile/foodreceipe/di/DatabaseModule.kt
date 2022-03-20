package com.mobile.foodreceipe.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobile.foodreceipe.database.RecipeDatabase
import com.mobile.foodreceipe.singletons.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,RecipeDatabase::class.java,Constants.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideRecipeDAO(recipeDatabase: RecipeDatabase)=recipeDatabase.recipeDao()

}