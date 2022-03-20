package com.mobile.foodreceipe.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.adapters.RecipeAdapter
import com.mobile.foodreceipe.databinding.FragmentReceipeBinding
import com.mobile.foodreceipe.singletons.NetworkListener
import com.mobile.foodreceipe.singletons.NetworkResult
import com.mobile.foodreceipe.singletons.observeOnce
import com.mobile.foodreceipe.viewmodels.FoodReceipeViewModel
import com.mobile.foodreceipe.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_receipe.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReceipeFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var recipeAdapter: RecipeAdapter
    lateinit var foodReceipeViewModel: FoodReceipeViewModel
    lateinit var receipeViewModel: RecipeViewModel
    private var fragmentBinding: FragmentReceipeBinding? = null
    private val binding get() = fragmentBinding!!
    private val args by navArgs<ReceipeFragmentArgs>()
    private lateinit var networkListener: NetworkListener

    override fun onQueryTextSubmit(query: String?): Boolean {

        if(query!=null){
            searchRecipeAPI(query)
        }
      return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentBinding = FragmentReceipeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        foodReceipeViewModel =
            activity?.let { ViewModelProvider(it).get(FoodReceipeViewModel::class.java) }!!

        receipeViewModel =
            activity?.let { ViewModelProvider(it).get(RecipeViewModel::class.java) }!!


        setHasOptionsMenu(true)

        binding.fabMenu.setOnClickListener {
            if(receipeViewModel.networkStatus){
                findNavController().navigate(R.id.action_receipeFragment_to_recipeBottomSheetFragment)
            }
            else
            {
                receipeViewModel.showNetworkStatus()
            }

        }

        setUpRecyclerview()

        receipeViewModel.readBackOnline.observe(viewLifecycleOwner,{
            receipeViewModel.backOnline=it
        })

        lifecycleScope.launch {
            networkListener= NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect {
                receipeViewModel.networkStatus=it
                receipeViewModel.showNetworkStatus()
                getRecipeDatabase()

            }

        }

        return fragmentBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_menu,menu)
        val search=menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        if (searchView != null) {
            searchView.isSubmitButtonEnabled=true
            searchView.setOnQueryTextListener(this)
        }

    }

    fun getRecipeDatabase() {
        lifecycleScope.launch {
            foodReceipeViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    recipeAdapter.setData(database[0].foodRecipe)
                    receipe_recyclerview.hideShimmer()
                } else {
                    getRecipeAPI()
                }
            })
        }
    }

    fun loadFromCache() {
        lifecycleScope.launch {
            foodReceipeViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    recipeAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    fun setUpRecyclerview() {
        recipeAdapter = RecipeAdapter()
        binding.receipeRecyclerview.adapter = recipeAdapter
        binding.receipeRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.receipeRecyclerview.showShimmer()

    }

    fun getRecipeAPI() {
        foodReceipeViewModel.getRecipe(receipeViewModel.applyQueries())
        foodReceipeViewModel.recipeResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    receipe_recyclerview.hideShimmer()
                    response.data.let { response.data?.let { it1 -> recipeAdapter.setData(it1) } }
                }
                is NetworkResult.Error -> {
                    loadFromCache()
                    receipe_recyclerview.hideShimmer()
                    Toast.makeText(activity, "${response.message}", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    receipe_recyclerview.showShimmer()
                }
            }

        })
    }

    fun searchRecipeAPI(searchQuery:String){
        receipe_recyclerview.showShimmer()
        foodReceipeViewModel.searchRecipe(receipeViewModel.applySearchQueries(searchQuery))
        foodReceipeViewModel.searchRecipeResponse.observe(viewLifecycleOwner,{response->
            when(response){

                is NetworkResult.Success -> {
                    receipe_recyclerview.hideShimmer()
                    val foodRecipe=response.data
                    foodRecipe?.let { recipeAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    receipe_recyclerview.hideShimmer()
                    loadFromCache()
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    receipe_recyclerview.showShimmer()
                }

            }

        })


    }



    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding = null
    }


}