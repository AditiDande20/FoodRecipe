package com.mobile.foodreceipe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.adapters.IngredientsAdapter
import com.mobile.foodreceipe.models.Result
import com.mobile.foodreceipe.singletons.Constants


class IngredientsFragment : Fragment() {
    val ingredientsAdapter : IngredientsAdapter by lazy { IngredientsAdapter() }
    lateinit var ingredients_recyclerview : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)
        ingredients_recyclerview = view.findViewById(R.id.recyclerview_ingredients)

        val args = arguments
        val bundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT)

        setupRecyclerView(ingredients_recyclerview)

        bundle?.extendedIngredients.let {
            if (it != null) {
                ingredientsAdapter.setData(it)
            }
        }

        return view
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter=ingredientsAdapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
    }

}