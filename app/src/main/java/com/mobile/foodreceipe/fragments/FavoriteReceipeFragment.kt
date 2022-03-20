package com.mobile.foodreceipe.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.adapters.FavoritesAdapter
import com.mobile.foodreceipe.databinding.FragmentFavoriteReceipeBinding
import com.mobile.foodreceipe.viewmodels.FoodReceipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteReceipeFragment : Fragment() {

    val recipeViewModel: FoodReceipeViewModel by viewModels()
    val favoriteAdapter: FavoritesAdapter by lazy { FavoritesAdapter(requireActivity(),recipeViewModel) }
    private var favoriteReceipeBinding: FragmentFavoriteReceipeBinding? = null
    private val binding get() = favoriteReceipeBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        favoriteReceipeBinding = FragmentFavoriteReceipeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = recipeViewModel
        binding.adapter = favoriteAdapter

        setHasOptionsMenu(true)

        setUpRecyclerview(binding.favoriteReceipeRecyclerview)

        return binding.root
    }

    private fun setUpRecyclerview(recyclerView: RecyclerView) {
        recyclerView.adapter = favoriteAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_All){
            recipeViewModel.deleteAllFavoriteRecipe()
            Snackbar.make(binding.root,"All Recipes Removed !!!",Snackbar.LENGTH_SHORT).setAction("OKAY"){}.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        favoriteReceipeBinding = null
        favoriteAdapter.closeContextualActionMode()
        super.onDestroyView()
    }
}