package com.muslimlife.tf.footballappkotlang.features.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.features.favorite.FavoritesMatchFragment
import com.muslimlife.tf.footballappkotlang.features.home.HomeFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val homeFragment = HomeFragment.newInstance()
        homeFragment.setOnLeagueSelectedListener{
            supportActionBar?.title = it
        }
        val favoriteFragment = FavoritesMatchFragment.newInstance()
        bnv_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    replaceContainer(homeFragment)
                    true
                }
                R.id.nav_favorite -> {
                    replaceContainer(favoriteFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

        bnv_main.selectedItemId = R.id.nav_home

    }

    private fun replaceContainer(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_fragment_container, fragment, fragment.javaClass.simpleName)
            .commitAllowingStateLoss()
    }

}