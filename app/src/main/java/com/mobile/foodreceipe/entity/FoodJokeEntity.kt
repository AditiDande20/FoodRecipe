package com.mobile.foodreceipe.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.foodreceipe.models.FoodJoke
import com.mobile.foodreceipe.singletons.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
data class FoodJokeEntity(@Embedded var foodJoke: FoodJoke) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}