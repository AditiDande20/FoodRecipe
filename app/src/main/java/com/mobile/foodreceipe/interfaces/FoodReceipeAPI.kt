package com.mobile.foodreceipe.interfaces

import com.mobile.foodreceipe.models.FoodJoke
import com.mobile.foodreceipe.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodReceipeAPI {

    @GET("/recipes/complexSearch")
    suspend fun getReceipes(@QueryMap queries : Map<String,String>) : Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchReceipes(@QueryMap searchQueries : Map<String,String>) : Response<FoodRecipe>

    @GET("/food/jokes/random")
    suspend fun getFoodJokes(@Query("apiKey") apiKey : String) : Response<FoodJoke>

}