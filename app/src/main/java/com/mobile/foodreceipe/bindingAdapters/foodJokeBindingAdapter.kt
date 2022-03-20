package com.mobile.foodreceipe.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.mobile.foodreceipe.entity.FoodJokeEntity
import com.mobile.foodreceipe.models.FoodJoke
import com.mobile.foodreceipe.singletons.NetworkResult

class foodJokeBindingAdapter {
    companion object {
        @BindingAdapter("foodJokeApiResponse", "foodJokeDatabase", requireAll = false)
        @JvmStatic
        fun handleVisibilityCardAndProgressBar(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {

            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }


        @BindingAdapter("errorApiResponse","errorDatabase",requireAll = true)
        @JvmStatic
        fun setErrorVisibility(view :View, apiResponse: NetworkResult<FoodJoke>?, database: List<FoodJokeEntity>?){
            if(database.isNullOrEmpty()){
                view.visibility=View.VISIBLE
                if(view is TextView){
                    if(apiResponse!=null){
                        view.text=apiResponse.message.toString()
                    }
                    else{
                        view.text="Something went wrong"
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
                view.visibility =View.INVISIBLE
            }
        }
    }
}