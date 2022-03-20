package com.mobile.foodreceipe.bindingAdapters

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.mobile.foodreceipe.adapters.FavoritesAdapter
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.todkars.shimmer.ShimmerRecyclerView

class FavoriteRecipeBindingAdapter {

    companion object {

        @BindingAdapter("viewVisibility","setData",requireAll = false)
        @JvmStatic
        fun handleViewVisibility(
            view: View,
            favoriteList: List<FavoritesRecipeEntity>?,
            adapter: FavoritesAdapter?
        ) {
            if (favoriteList.isNullOrEmpty()) {
                when (view) {

                    is ConstraintLayout -> view.visibility = View.VISIBLE
                    is ShimmerRecyclerView -> view.visibility = View.GONE

                }
            } else {
                when (view) {

                    is ConstraintLayout -> view.visibility = View.GONE
                    is ShimmerRecyclerView -> {
                        view.visibility = View.VISIBLE
                        adapter?.setData(favoriteList)

                    }
                }
            }

        }

    }

}