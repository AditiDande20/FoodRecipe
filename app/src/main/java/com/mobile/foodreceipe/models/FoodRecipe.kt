package com.mobile.foodreceipe.models


import com.google.gson.annotations.SerializedName
import com.mobile.foodreceipe.models.Result

data class FoodRecipe(
        @SerializedName("results")
    val results: List<Result>,

        )