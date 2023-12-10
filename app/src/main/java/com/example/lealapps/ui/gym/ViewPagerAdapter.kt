package com.example.lealapps.ui.gym

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.lealapps.ui.home.HomeFragment

private const val NUM_TABS = 2

class ViewPagerAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                ExerciseFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}