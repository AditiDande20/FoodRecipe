package com.mobile.foodreceipe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.models.Result
import com.mobile.foodreceipe.singletons.Constants
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val bundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT)

        view.recipe_image.load(bundle?.image)
        view.title.text = bundle?.title
        view.recipe_likes.text = bundle?.aggregateLikes.toString()
        view.recipe_time.text = bundle?.readyInMinutes.toString()
        bundle?.summary.let {
            val desc = Jsoup.parse(it).text()
            view.description.text = desc
        }

        if (bundle?.vegetarian == true) {
            view.image_veg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.text_veg.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (bundle?.vegan == true) {
            view.image_vegan.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.text_vegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (bundle?.dairyFree == true) {
            view.image_dairy_free.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.text_dairy_free.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (bundle?.veryHealthy == true) {
            view.image_healthy.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.text_healthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (bundle?.cheap == true) {
            view.image_cheap.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.text_cheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (bundle?.glutenFree == true) {
            view.image_gluten_free.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            view.text_gluten_free.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        return view
    }

}