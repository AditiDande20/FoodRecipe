package com.mobile.foodreceipe.fragments

import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.singletons.Constants
import com.mobile.foodreceipe.viewmodels.FoodReceipeViewModel
import com.mobile.foodreceipe.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.recipe_bottom_sheet.view.*
import java.lang.Exception
import java.util.*

class RecipeBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var recipeViewModel:RecipeViewModel
    var mealType=Constants.DEFAULT_MEAL_TYPE
    var mealTypeID=0
    var dietType=Constants.DEFAULT_DIET_TYPE
    var dietTypeID=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeViewModel =
            activity?.let { ViewModelProvider(it).get(RecipeViewModel::class.java) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.recipe_bottom_sheet, container, false)

        recipeViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,{value->

            mealType=value.selectedMealType
            dietType=value.selectedDietType

            updateChip(value.selectedMealTypeID,view.chip_group_meal_type)
            updateChip(value.selectedDietTypeID,view.chip_group_diet_type)

        })

        view.chip_group_diet_type.setOnCheckedChangeListener { group, selectedChipID ->
            val chip=group.findViewById<Chip>(selectedChipID)
            val selectedDietType=chip.text.toString().toLowerCase(Locale.ROOT)
            dietType=selectedDietType
            dietTypeID=selectedChipID

        }

        view.chip_group_meal_type.setOnCheckedChangeListener { group, selectedChipID ->
            val chip=group.findViewById<Chip>(selectedChipID)
            val selecteMealType=chip.text.toString().toLowerCase(Locale.ROOT)
            mealType=selecteMealType
            mealTypeID=selectedChipID

        }

        view.apply_btn.setOnClickListener {
            recipeViewModel.saveMealAndDietType(mealType,mealTypeID,dietType,dietTypeID)
            val action=RecipeBottomSheetFragmentDirections.actionRecipeBottomSheetFragmentToReceipeFragment(true)
            findNavController().navigate(action)
        }

        return view
    }

    private fun updateChip(chipID: Int, chipGroup: ChipGroup) {
        if(chipID!=0){
            try {
                chipGroup.findViewById<Chip>(chipID).isChecked=true
            }
            catch (e:Exception){

            }

        }

    }

}