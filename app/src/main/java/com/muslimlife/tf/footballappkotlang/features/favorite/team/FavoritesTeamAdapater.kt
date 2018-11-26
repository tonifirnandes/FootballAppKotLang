package com.muslimlife.tf.footballappkotlang.features.favorite.team

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.features.team.detail.TeamDetailActivity
import kotlinx.android.synthetic.main.football_team_item.view.*
import org.jetbrains.anko.startActivity

class FavoritesTeamAdapater(private val teamList: List<FavoriteTeam>, private val context: Context?) :
    RecyclerView.Adapter<FavoritesTeamAdapater.TeamViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.football_team_item, p0, false))
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(p0: TeamViewHolder, p1: Int) {
        p0.bind(teamList[p1])
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: FavoriteTeam) {
            itemView.tv_team_name.text = team.teamName
            Glide.with(itemView.iv_team_logo)
                .load(team.teamLogoUrl)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(itemView.iv_team_logo)

            //team detail
            itemView.setOnClickListener {
                itemView.context.startActivity<TeamDetailActivity>(TeamDetailActivity.arg_favorite_team_bundle_key to team)
            }
        }
    }
}