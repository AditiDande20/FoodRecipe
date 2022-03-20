package com.mobile.foodreceipe.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.foodreceipe.singletons.Constants
import com.mobile.foodreceipe.singletons.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(application: Application, private val dataStoreRepository: DataStoreRepository) :AndroidViewModel(application) {

    var mealType:String=Constants.DEFAULT_MEAL_TYPE
    var dietType:String=Constants.DEFAULT_DIET_TYPE

    val readMealAndDietType=dataStoreRepository.readMealAndDietType
    val readBackOnline=dataStoreRepository.readBackOnline.asLiveData()

    var networkStatus=false
    var backOnline=false

    fun saveMealAndDietType(selectedMealType:String, selectedMealTypeId:Int, selectedDietType:String,selectedDietTypeId:Int){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveMealAndDietType(selectedMealType,selectedMealTypeId,selectedDietType,selectedDietTypeId)
        }
    }

    fun saveBackOnline(backOnline:Boolean){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }

    fun applyQueries(): Map<String, String> {

        viewModelScope.launch {

            readMealAndDietType.collect { preference->
                mealType=preference.selectedMealType
                dietType=preference.selectedMealType

            }

        }

        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGRIDIENTS] = "true"
        return queries
    }

    fun applySearchQueries(queryString :String) : HashMap<String,String>{
        var queries=HashMap<String,String> ()
        queries[Constants.QUERY_SEARCH]=queryString
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGRIDIENTS] = "true"
        return queries

    }

    fun showNetworkStatus(){
        if(!networkStatus){
            Toast.makeText(getApplication(),"No Internet Connection",Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        }
        else if(networkStatus){
            if(backOnline){
                Toast.makeText(getApplication(),"We are back Online !!!",Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}