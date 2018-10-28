package com.muslimlife.tf.footballappkotlang.features.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.muslimlife.tf.footballappkotlang.features.home.views.ClubItemCardView
import com.muslimlife.tf.footballappkotlang.model.ClubInfo
import org.jetbrains.anko.AnkoContext

class ClubItemAdapter(private val clubItemList: List<ClubInfo>, private val listener: (ClubInfo) -> Unit) :
    RecyclerView.Adapter<ClubItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ClubItemCardView().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = clubItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(clubItemList[position], listener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val clubName: TextView = itemView.findViewById(ClubItemCardView.tvClubNameId)
        private val clubLogo: ImageView = itemView.findViewById(ClubItemCardView.ivClubLogoId)

        fun bindItem(club: ClubInfo, listener: (ClubInfo) -> Unit) {
            clubName.text = club.name
            Glide.with(itemView).load(club.logoDrawableId).into(clubLogo)
            itemView.setOnClickListener { listener(club) }
        }
    }
}