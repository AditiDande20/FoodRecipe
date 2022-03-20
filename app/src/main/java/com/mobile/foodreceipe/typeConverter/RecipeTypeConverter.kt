package com.mobile.foodreceipe.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobile.foodreceipe.models.FoodRecipe
import com.mobile.foodreceipe.models.Result

class RecipeTypeConverter {

    val gson=Gson()

    @TypeConverter
    fun JSONtoString(foodRecipe: FoodRecipe):String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun StringToJSON(data:String):FoodRecipe{
        val listtype=object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data,listtype)
    }

    @TypeConverter
    fun stringtoResult(result: Result) : String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun StringToResult(data:String):Result{
        val listtype=object : TypeToken<Result>() {}.type
        return gson.fromJson(data,listtype)
    }

}