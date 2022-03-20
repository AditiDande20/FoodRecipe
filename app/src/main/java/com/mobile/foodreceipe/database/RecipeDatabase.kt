package com.mobile.foodreceipe.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobile.foodreceipe.dao.RecipeDAO
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.mobile.foodreceipe.entity.FoodJokeEntity
import com.mobile.foodreceipe.entity.RecipeEntity
import com.mobile.foodreceipe.typeConverter.RecipeTypeConverter

@Database(entities = [RecipeEntity::class,FavoritesRecipeEntity::class,FoodJokeEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase :RoomDatabase() {
    abstract fun recipeDao():RecipeDAO
}