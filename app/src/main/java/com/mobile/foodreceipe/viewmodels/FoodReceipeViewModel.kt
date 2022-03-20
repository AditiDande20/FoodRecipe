package com.mobile.foodreceipe.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.*
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.mobile.foodreceipe.entity.FoodJokeEntity
import com.mobile.foodreceipe.entity.RecipeEntity
import com.mobile.foodreceipe.models.FoodJoke
import com.mobile.foodreceipe.models.FoodRecipe
import com.mobile.foodreceipe.repository.FoodReceipeRepository
import com.mobile.foodreceipe.singletons.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FoodReceipeViewModel @Inject constructor(
    application: Application,
    private val foodReceipeRepository: FoodReceipeRepository,
    private val context: Context
) : AndroidViewModel(application) {

    /** Room Database */
    val readRecipes: LiveData<List<RecipeEntity>> = foodReceipeRepository.local.readRecipe().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoritesRecipeEntity>> = foodReceipeRepository.local.readFavoriteRecipe().asLiveData()
    val readFoodJokes: LiveData<List<FoodJokeEntity>> = foodReceipeRepository.local.readFoodJoke().asLiveData()

    private fun insertRecipe(recipeEntity: RecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            foodReceipeRepository.local.insertRecipe(recipeEntity)
        }

     fun insertFavoriteRecipe(favoritesRecipeEntity: FavoritesRecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            foodReceipeRepository.local.insertFavoriteRecipe(favoritesRecipeEntity)
        }

    private fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            foodReceipeRepository.local.insertFoodJoke(foodJokeEntity)
        }

     fun deleteFavoriteRecipe(favoritesRecipeEntity: FavoritesRecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            foodReceipeRepository.local.deleteFavoriteRecipe(favoritesRecipeEntity)
        }

     fun deleteAllFavoriteRecipe() =
        viewModelScope.launch(Dispatchers.IO) {
            foodReceipeRepository.local.deleteAllFavoriteRecipe()
        }

    /** Retrofit */
    val recipeResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val searchRecipeResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun getRecipe(query: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(query)
    }

    fun searchRecipe(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodJoke(apiKey:String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (isNetworkConnected(context)) {
            try {
                val response = foodReceipeRepository.remote.getFoodJokes(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value!!.data
                if(foodJoke!=null){
                    offlineCacheFoodJokes(foodJoke)
                }
            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error(message = "No Jokes Found")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }


    private suspend fun getRecipesSafeCall(query: Map<String, String>) {
        recipeResponse.value = NetworkResult.Loading()
        if (isNetworkConnected(context)) {
            try {
                val response = foodReceipeRepository.remote.getReceipe(query)
                recipeResponse.value = handleFoodReceipeResponse(response)

                val foodReceipe = recipeResponse.value!!.data
                if (foodReceipe != null) {
                    offlineCacheRecipes(foodReceipe)
                }

            } catch (e: Exception) {
                recipeResponse.value = NetworkResult.Error(message = "No Recipes Found")
            }
        } else {
            recipeResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipeResponse.value = NetworkResult.Loading()
        if (isNetworkConnected(context)) {
            try {
                val response = foodReceipeRepository.remote.searchReceipe(searchQuery)
                searchRecipeResponse.value = handleFoodReceipeResponse(response)
            } catch (e: Exception) {
                searchRecipeResponse.value = NetworkResult.Error(message = "No Recipes Found")
            }
        } else {
            searchRecipeResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipeEntity = RecipeEntity(foodRecipe)
        insertRecipe(recipeEntity)
    }

    private fun offlineCacheFoodJokes(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun handleFoodReceipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "API key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "No Recipes Found")
            }
            response.isSuccessful -> {
                val foodReceipe = response.body()
                return NetworkResult.Success(foodReceipe!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error(message = "API key Limited")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                NetworkResult.Success(foodJoke!!)
            }
            else -> {
                NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}