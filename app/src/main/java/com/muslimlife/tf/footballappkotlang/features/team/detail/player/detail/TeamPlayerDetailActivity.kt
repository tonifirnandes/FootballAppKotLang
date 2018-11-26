package com.muslimlife.tf.footballappkotlang.features.team.detail.player.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Player
import kotlinx.android.synthetic.main.player_detail_activity.*
import kotlinx.android.synthetic.main.player_info_detail_layout.*

class TeamPlayerDetailActivity : AppCompatActivity() {

    companion object {
        const val arg_player_bundle_key: String = "player"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail_activity)
        val player = intent.getParcelableExtra<Player>(arg_player_bundle_key)
        supportActionBar?.title = player.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView(player)
    }

    private fun initView(player: Player?) {
        if (player != null) {
            Glide.with(iv_player_banner)
                .load(player.fanArt1Url)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(iv_player_banner)
            tv_player_weight_value.text = player.weight
            tv_player_height_value.text = player.height
            tv_player_position.text = player.position
            tv_player_description.text = player.descriptionInEnglish
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

}