package com.muslimlife.tf.footballappkotlang.features.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.features.detail.DetailActivity
import com.muslimlife.tf.footballappkotlang.features.home.views.HomeActivityView
import com.muslimlife.tf.footballappkotlang.model.ClubInfo
import org.jetbrains.anko.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeActivityView(getData()){
            goToDetailActivity(it)
        }.setContentView(this)
    }

    fun getData() : (List<ClubInfo>) {
        val items: MutableList<ClubInfo> = mutableListOf()
        val clubNameList = resources.getStringArray(R.array.club_name_list)
        val clubLogoList = resources.obtainTypedArray(R.array.club_logo_list)
        val clubDescriptionsList = resources.getStringArray(R.array.club_descriptions_list)
        items.clear()
        for (i in clubNameList.indices) {
            items.add(ClubInfo(
                name = clubNameList[i],
                logoDrawableId = clubLogoList.getResourceId(i, 0),
                descriptions = clubDescriptionsList[i])
            )
        }

        //Recycle the typed array
        clubLogoList.recycle()

        return items
    }

    fun goToDetailActivity(club: ClubInfo) {
        startActivity(intentFor<DetailActivity>(DetailActivity.intentExtrasKey to club).clearTask())
    }
}