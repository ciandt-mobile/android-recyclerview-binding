package com.ciandt.recyclerviewbinding.presentation.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ciandt.recyclerviewbinding.presentation.endless.EndlessFragment
import com.ciandt.recyclerviewbinding.presentation.simple.SimpleFragment

class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {

        return if (fragments.indices.contains(position)) {
            fragments[position]
        } else {

            val frg = if (position == 0) {
                SimpleFragment()
            } else  {
                EndlessFragment()
            }

            fragments.add(position, frg)

            frg
        }
    }

    override fun getCount() = 2
}