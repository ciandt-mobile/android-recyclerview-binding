package com.ciandt.recyclerviewbinding.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ciandt.recyclerviewbinding.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)

        bottomNavigation.setOnNavigationItemSelectedListener {

            val result = when(it.itemId) {
                 R.id.action_simple -> {
                    viewpager.setCurrentItem(0, false)
                    true
                }
                R.id.action_endless -> {
                    viewpager.setCurrentItem(1, false)
                    true
                }
                else -> false
            }

            result
        }
    }
}