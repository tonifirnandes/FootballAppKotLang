package com.muslimlife.tf.footballappkotlang.features.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.base_tab_fragments.*

class BaseFavoriteFragment : Fragment() {
    companion object {
        fun newInstance(): BaseFavoriteFragment = BaseFavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_tab_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager_main.adapter = BaseFavoriteTabsAdapater(
            fragmentManager,
            resources.getStringArray(R.array.favorite_menus)
        )
        tabs_main.setupWithViewPager(viewpager_main)
        viewpager_main.show()
        tabs_main.show()
    }
}