package com.muslimlife.tf.footballappkotlang.features.home.views

import android.support.v4.widget.TextViewCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.muslimlife.tf.footballappkotlang.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ClubItemCardView : AnkoComponent<ViewGroup> {

    companion object {
        const val tvClubNameId = 1
        const val ivClubLogoId = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)

            cardView {
                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    padding = dimen(R.dimen.normal_space_size)

                    imageView {
                        id = ivClubLogoId
                    }.lparams(
                        width = dimen(R.dimen.home_club_logo_size),
                        height = dimen(R.dimen.home_club_logo_size)) {
                        gravity = Gravity.CENTER
                    }

                    textView {
                        id = tvClubNameId
                        TextViewCompat.setTextAppearance(this, R.style.TextAppearance_AppCompat_Medium)
                    }.lparams(width = matchParent, height = wrapContent) {
                        gravity = Gravity.CENTER
                        leftMargin = dimen(R.dimen.normal_space_size)
                    }
                }
            }.lparams(width = matchParent, height = wrapContent) {
                margin = dimen(R.dimen.small_space_size)
            }
        }
    }

}