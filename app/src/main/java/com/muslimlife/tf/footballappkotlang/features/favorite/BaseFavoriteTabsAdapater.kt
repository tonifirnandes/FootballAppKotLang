package com.muslimlife.tf.footballappkotlang.features.favorite

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.muslimlife.tf.footballappkotlang.features.favorite.match.FavoritesMatchFragment
import com.muslimlife.tf.footballappkotlang.features.favorite.team.FavoriteTeamsFragment

class BaseFavoriteTabsAdapater(
    fm: FragmentManager?,
    private val favoriteMenus: Array<String>
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> FavoritesMatchFragment.newInstance()
            1 -> FavoriteTeamsFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getCount(): Int = favoriteMenus.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> favoriteMenus[0]
            1 -> favoriteMenus[1]
            else -> ""
        }
    }

}