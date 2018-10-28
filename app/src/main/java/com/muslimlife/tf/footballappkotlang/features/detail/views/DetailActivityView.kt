package com.muslimlife.tf.footballappkotlang.features.detail.views

import android.graphics.Color
import android.support.v4.widget.TextViewCompat
import android.view.Gravity
import android.view.View
import com.bumptech.glide.Glide
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.features.detail.DetailActivity
import com.muslimlife.tf.footballappkotlang.model.ClubInfo
import org.jetbrains.anko.*

class DetailActivityView(private val club: ClubInfo?) : AnkoComponent<DetailActivity>{
    override fun createView(ui: AnkoContext<DetailActivity>): View = with(ui){

        relativeLayout {

            verticalLayout {

                backgroundColor = Color.WHITE

                club?.let {
                    val clubLogoIv = imageView ().lparams(
                        width = dimen(R.dimen.detail_club_logo_size),
                        height = dimen(R.dimen.detail_club_logo_size)) {
                        gravity = Gravity.CENTER

                    }
                    Glide.with(this).load(it.logoDrawableId).into(clubLogoIv)

                    textView {
                        text = it.name
                        TextViewCompat.setTextAppearance(this, R.style.TextAppearance_AppCompat_Medium)
                        gravity = Gravity.CENTER
                    }.lparams(width = matchParent, height = wrapContent) {
                        gravity = Gravity.CENTER
                        padding = dimen(R.dimen.normal_space_size)
                        topMargin = dimen(R.dimen.detail_title_top_space_size)
                    }

                    textView {
                        this.text = it.descriptions
                        TextViewCompat.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body1)
                    }.lparams(width = matchParent, height = wrapContent) {
                        gravity = Gravity.CENTER
                        padding = dimen(R.dimen.normal_space_size)
                        topMargin = dimen(R.dimen.detail_body_top_space_size)
                    }
                } ?: toast("Sorry we cannot process your request at the moment, please try again later")


            }.lparams(width = matchParent, height = wrapContent) {
                margin = dimen(R.dimen.normal_space_size)
            }
        }

    }

}