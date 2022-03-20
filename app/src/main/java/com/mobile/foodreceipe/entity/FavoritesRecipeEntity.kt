package com.mobile.foodreceipe.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.foodreceipe.models.Result
import com.mobile.foodreceipe.singletons.Constants

@Entity(tableName = Constants.FAVORITE_RECIPE_TABLE)
data class FavoritesRecipeEntity constructor(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var result: Result){

}
