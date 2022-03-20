package com.mobile.foodreceipe.datasource

import com.mobile.foodreceipe.interfaces.FoodReceipeAPI
import com.mobile.foodreceipe.models.FoodJoke
import com.mobile.foodreceipe.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodReceipeAPI: FoodReceipeAPI) {


    suspend fun getReceipe(query:Map<String,String>):Response<FoodRecipe>{
        return foodReceipeAPI.getReceipes(query)
    }

    suspend fun searchReceipe(searchQuery:Map<String,String>):Response<FoodRecipe>{
        return foodReceipeAPI.searchReceipes(searchQuery)
    }

    suspend fun getFoodJokes(apiKey : String) : Response<FoodJoke>{
        return foodReceipeAPI.getFoodJokes(apiKey)
    }

}