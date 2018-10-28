package com.muslimlife.tf.footballappkotlang.features.home.views

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.muslimlife.tf.footballappkotlang.features.home.HomeActivity
import com.muslimlife.tf.footballappkotlang.features.home.adapter.ClubItemAdapter
import com.muslimlife.tf.footballappkotlang.model.ClubInfo
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class HomeActivityView(private val clubList: List<ClubInfo>, private val listener: (ClubInfo) -> Unit) : AnkoComponent<HomeActivity>{
    override fun createView(ui: AnkoContext<HomeActivity>): View = with(ui){
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            recyclerView {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = ClubItemAdapter(clubList, listener)
            }.lparams(width = matchParent, height = matchParent)
        }
    }

}