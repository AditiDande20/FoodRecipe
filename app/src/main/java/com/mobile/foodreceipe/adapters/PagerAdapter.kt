package com.mobile.foodreceipe.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter (var bundle : Bundle,
                    var fragmentList :ArrayList<Fragment>,
                    var titles :ArrayList<String>,
                    var fm :FragmentManager) : FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        fragmentList[position].arguments=bundle
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}