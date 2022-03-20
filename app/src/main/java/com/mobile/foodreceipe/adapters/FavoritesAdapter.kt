package com.mobile.foodreceipe.adapters

import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.databinding.FavoritesRowLayoutBinding
import com.mobile.foodreceipe.diffUtil.RecipeDiffUtil
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.mobile.foodreceipe.fragments.FavoriteReceipeFragmentDirections
import com.mobile.foodreceipe.viewmodels.FoodReceipeViewModel
import com.mobile.foodreceipe.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.favorites_row_layout.view.*

class FavoritesAdapter(private val requireActivity: FragmentActivity, private val foodRecipeViewModel: FoodReceipeViewModel) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>(), ActionMode.Callback {

    private var favoriteRecipeList = emptyList<FavoritesRecipeEntity>()
    private var multiSelect = false
    private var selectedRecipeList = arrayListOf<FavoritesRecipeEntity>()
    private var holdersList = arrayListOf<FavoriteViewHolder>()
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView :View

    class FavoriteViewHolder(private val favoritesRowLayoutBinding: FavoritesRowLayoutBinding) :
        RecyclerView.ViewHolder(favoritesRowLayoutBinding.root) {

        fun bind(favoritesRecipeEntity: FavoritesRecipeEntity) {
            favoritesRowLayoutBinding.favorites = favoritesRecipeEntity
            favoritesRowLayoutBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoritesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        rootView=holder.itemView.rootView
        holdersList.add(holder)
        val currentRecipe = favoriteRecipeList[position]
        holder.bind(currentRecipe)

        holder.itemView.favoritesRecipeRowLayout.setOnClickListener {
            if (multiSelect) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteReceipeFragmentDirections.actionFavoriteReceipeFragmentToDetailActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }

        }

        holder.itemView.setOnLongClickListener {
            if (!multiSelect) {
                multiSelect = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                applySelection(holder, currentRecipe)
                true
            }

        }
    }

    override fun getItemCount(): Int {
        return favoriteRecipeList.size
    }

    fun setData(newFavoriteRecipeList: List<FavoritesRecipeEntity>) {
        val favoriteRecipeDiffUtil = RecipeDiffUtil(favoriteRecipeList, newFavoriteRecipeList)
        val diffUtil = DiffUtil.calculateDiff(favoriteRecipeDiffUtil)
        favoriteRecipeList = newFavoriteRecipeList
        diffUtil.dispatchUpdatesTo(this)

    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun changeRecipeLayout(
        holder: FavoriteViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {

        holder.itemView.favorite_card.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.favorite_card.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)

    }

    private fun applySelection(
        holder: FavoriteViewHolder,
        currentRecipeEntity: FavoritesRecipeEntity
    ) {
        if (selectedRecipeList.contains(currentRecipeEntity)) {
            selectedRecipeList.remove(currentRecipeEntity)
            changeRecipeLayout(holder, R.color.cardBackgroundColor, R.color.cardStrokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipeList.add(currentRecipeEntity)
            changeRecipeLayout(holder, R.color.cardLightBackgroundColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun applyActionModeTitle() {
        when (selectedRecipeList.size) {
            0 -> {
                mActionMode.finish()
                multiSelect = false
            }
            else -> {
                mActionMode.title = "${selectedRecipeList.size}"
            }
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        if (actionMode != null) {
            mActionMode = actionMode
        }
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if(menu?.itemId == R.id.delete){
            selectedRecipeList.forEach {
                foodRecipeViewModel.deleteFavoriteRecipe(it)
            }
            Snackbar.make(rootView,"${selectedRecipeList.size} Recipe/s Removed !!!",Snackbar.LENGTH_SHORT).setAction("OKAY"){}.show()
            multiSelect=false
            selectedRecipeList.clear()
            mActionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        holdersList.forEach { holder ->
            changeRecipeLayout(holder, R.color.cardBackgroundColor, R.color.cardStrokeColor)
        }
        multiSelect = false
        selectedRecipeList.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    fun closeContextualActionMode(){
        if(this :: mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}