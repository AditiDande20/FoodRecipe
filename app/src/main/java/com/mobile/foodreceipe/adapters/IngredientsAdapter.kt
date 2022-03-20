package com.mobile.foodreceipe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.diffUtil.RecipeDiffUtil
import com.mobile.foodreceipe.models.ExtendedIngredient
import com.mobile.foodreceipe.singletons.Constants
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    var ingredientsList:List<ExtendedIngredient> = emptyList()

    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {

        val currentIngredient=ingredientsList[position]

        holder.itemView.ingredient_image.load(Constants.INGREDIENT_BASE_URL + currentIngredient.image){
            crossfade(600)
            error(R.drawable.ic_image_placeholder)
        }
        holder.itemView.ingredient_name.text=currentIngredient.name.capitalize(Locale.ROOT)
        holder.itemView.ingredient_amount.text=currentIngredient.amount.toString()
        holder.itemView.ingredient_unit.text=currentIngredient.unit
        holder.itemView.ingredient_consistency.text=currentIngredient.consistency
        holder.itemView.ingredient_original.text=currentIngredient.original

    }

    override fun getItemCount(): Int {
    return ingredientsList.size
    }

    fun setData(newIngredient : List<ExtendedIngredient>){

        val ingredientDiffUtil = RecipeDiffUtil(ingredientsList,newIngredient)
        val ingredientDiffResult = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredientsList=newIngredient
        ingredientDiffResult.dispatchUpdatesTo(this)

    }

}