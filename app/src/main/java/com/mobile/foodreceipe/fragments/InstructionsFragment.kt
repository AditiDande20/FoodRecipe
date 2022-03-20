package com.mobile.foodreceipe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.models.Result
import com.mobile.foodreceipe.singletons.Constants
import kotlinx.android.synthetic.main.fragment_instructions.view.*


class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val bundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT)

        view.webview_instructions.webViewClient = object : WebViewClient(){}
        val websiteURL= bundle?.sourceUrl
        if (websiteURL != null) {
            view.webview_instructions.loadUrl(websiteURL)
        }

        return view
    }

}