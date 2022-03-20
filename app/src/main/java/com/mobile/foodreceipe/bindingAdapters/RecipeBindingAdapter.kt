package com.mobile.foodreceipe.bindingAdapters

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.mobile.foodreceipe.entity.RecipeEntity
import com.mobile.foodreceipe.models.FoodRecipe
import com.mobile.foodreceipe.singletons.NetworkResult

class RecipeBindingAdapter {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun handleErrorVisibility(
            constraintLayout: ConstraintLayout,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipeEntity>?
        ) {

            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                constraintLayout.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Success || apiResponse is NetworkResult.Loading) {
                constraintLayout.visibility = View.INVISIBLE

            }
        }

    }

}