package com.mobile.foodreceipe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.databinding.FragmentFoodJokeBinding
import com.mobile.foodreceipe.singletons.Constants
import com.mobile.foodreceipe.singletons.NetworkResult
import com.mobile.foodreceipe.viewmodels.FoodReceipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val foodRecipeViewModel by viewModels<FoodReceipeViewModel>()
    private var fragmentFoodJokeBinding: FragmentFoodJokeBinding? = null
    private val binding get() = fragmentFoodJokeBinding
    private var foodJoke= "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentFoodJokeBinding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewmodel = foodRecipeViewModel

        setHasOptionsMenu(true)

        foodRecipeViewModel.getFoodJoke(Constants.API_KEY)

        foodRecipeViewModel.foodJokeResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding?.textJoke?.text = response.data?.text
                    if(response.data!=null){
                        foodJoke=response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }
            }

        })

        return binding!!.root
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            foodRecipeViewModel.readFoodJokes.observe(viewLifecycleOwner, { database ->
                if (!database.isNullOrEmpty()) {
                    binding?.textJoke?.text = database[0].foodJoke.text
                    foodJoke=database[0].foodJoke.text
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share){
            val shareInt = Intent().apply {
                this.action=Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodJoke)
                this.type="text/plain"
            }
            startActivity(shareInt)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        fragmentFoodJokeBinding = null
        super.onDestroyView()
    }


}