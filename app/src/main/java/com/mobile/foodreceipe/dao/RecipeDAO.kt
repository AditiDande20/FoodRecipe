package com.mobile.foodreceipe.dao

import androidx.room.*
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.mobile.foodreceipe.entity.FoodJokeEntity
import com.mobile.foodreceipe.entity.RecipeEntity
import com.mobile.foodreceipe.models.FoodJoke
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipe_table")
    fun getRecipe(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesRecipeEntity: FavoritesRecipeEntity)

    @Query("SELECT * FROM favorite_recipe_table")
    fun getFavoriteRecipe(): Flow<List<FavoritesRecipeEntity>>

    @Delete
    fun deleteFavoriteRecipe(favoritesRecipeEntity: FavoritesRecipeEntity)

    @Query("DELETE FROM favorite_recipe_table")
    fun deleteAllFavoriteRecipe()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table")
    fun getFoodJoke(): Flow<List<FoodJokeEntity>>

}