package com.mobile.foodreceipe.datasource

import com.mobile.foodreceipe.dao.RecipeDAO
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.mobile.foodreceipe.entity.FoodJokeEntity
import com.mobile.foodreceipe.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipeDao:RecipeDAO){

    suspend fun insertRecipe(recipeEntity: RecipeEntity){
        return recipeDao.insertRecipe(recipeEntity)
    }

    fun readRecipe():Flow<List<RecipeEntity>>{
        return recipeDao.getRecipe()
    }

    suspend fun insertFavoriteRecipe(favoritesRecipeEntity: FavoritesRecipeEntity){
        return recipeDao.insertFavoriteRecipe(favoritesRecipeEntity)
    }

    fun readFavoriteRecipe():Flow<List<FavoritesRecipeEntity>>{
        return recipeDao.getFavoriteRecipe()
    }

    suspend fun deleteFavoriteRecipe(favoritesRecipeEntity: FavoritesRecipeEntity){
        return recipeDao.deleteFavoriteRecipe(favoritesRecipeEntity)
    }

    suspend fun deleteAllFavoriteRecipe(){
        return recipeDao.deleteAllFavoriteRecipe()
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        return recipeDao.insertFoodJoke(foodJokeEntity)
    }

    fun readFoodJoke():Flow<List<FoodJokeEntity>>{
        return recipeDao.getFoodJoke()
    }

}