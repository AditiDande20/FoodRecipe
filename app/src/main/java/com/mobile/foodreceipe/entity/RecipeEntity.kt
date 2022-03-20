package com.mobile.foodreceipe.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.foodreceipe.models.FoodRecipe
import com.mobile.foodreceipe.singletons.Constants

@Entity(tableName = Constants.RECIPE_TABLE)
data class RecipeEntity constructor(val foodRecipe: FoodRecipe){
    @PrimaryKey(autoGenerate = false)
    var id:Int =0
}