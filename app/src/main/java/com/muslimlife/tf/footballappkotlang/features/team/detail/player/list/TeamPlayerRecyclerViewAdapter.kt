package com.muslimlife.tf.footballappkotlang.features.team.detail.player.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.Player
import com.muslimlife.tf.footballappkotlang.features.team.detail.player.detail.TeamPlayerDetailActivity
import kotlinx.android.synthetic.main.footbal_team_player_item.view.*
import org.jetbrains.anko.startActivity

class TeamPlayerRecyclerViewAdapter(private val playerList: List<Player>, private val context: Context?) :
    RecyclerView.Adapter<TeamPlayerRecyclerViewAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.footbal_team_player_item, p0, false))
    }

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(p0: PlayerViewHolder, p1: Int) {
        p0.bind(playerList[p1])
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(player: Player) {
            Glide.with(itemView.iv_player_logo)
                .load(player.faceLogoUrl)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(itemView.iv_player_logo)
            itemView.tv_player_name.text = player.name
            itemView.tv_player_position.text = player.position
            //player detail
            itemView.setOnClickListener {
                itemView.context.startActivity<TeamPlayerDetailActivity>(TeamPlayerDetailActivity.arg_player_bundle_key to player)
            }


        }
    }
}