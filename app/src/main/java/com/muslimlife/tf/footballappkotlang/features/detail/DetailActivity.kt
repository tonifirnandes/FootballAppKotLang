package com.muslimlife.tf.footballappkotlang.features.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.muslimlife.tf.footballappkotlang.features.detail.views.DetailActivityView
import com.muslimlife.tf.footballappkotlang.model.ClubInfo
import org.jetbrains.anko.setContentView

class DetailActivity : AppCompatActivity() {

    companion object {
        const val intentExtrasKey = "intent_detail_extras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data: ClubInfo? = intent.getParcelableExtra(intentExtrasKey)
        DetailActivityView(data).setContentView(this)
    }

}