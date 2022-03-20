package com.mobile.foodreceipe.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import coil.size.Scale
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.fragments.ReceipeFragmentDirections
import com.mobile.foodreceipe.models.Result
import org.jsoup.Jsoup
import java.lang.Exception

class RecipeRowBindingAdapter {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) {
            try {
                recipeRowLayout.setOnClickListener {
                    val action =
                        ReceipeFragmentDirections.actionReceipeFragmentToDetailActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }
            } catch (e: Exception) {
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, number: Int) {
            textView.text = number.toString()
        }

        @BindingAdapter("setMinutes")
        @JvmStatic
        fun setMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }


        @BindingAdapter("setVegan")
        @JvmStatic
        fun setVegan(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }

        @BindingAdapter("loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imageView: ImageView, imageURL: String) {
            imageView.load(imageURL) {
                crossfade(600)
                scale(Scale.FILL)
                error(R.drawable.ic_image_placeholder)
            }
        }

        @BindingAdapter("parseHTML")
        @JvmStatic
        fun parseHTML(textView: TextView, description:String) {
            val desc=Jsoup.parse(description).text()
            textView.text=desc
        }

    }
}