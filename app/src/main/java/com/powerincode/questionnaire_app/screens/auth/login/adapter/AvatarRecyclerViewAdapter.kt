package com.powerincode.questionnaire_app.screens.auth.login.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.data.local.avatars.Avatar
import com.powerincode.questionnaire_app.core.views.AvatarView
import kotlinx.android.synthetic.main.item_avatar_list.view.*

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AvatarRecyclerViewAdapter : RecyclerView.Adapter<AvatarRecyclerViewAdapter.AvatarViewHolder>() {
    override fun onCreateViewHolder(p0 : ViewGroup, p1 : Int) : AvatarViewHolder {
        return AvatarViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_avatar_list, p0, false))
    }

    override fun getItemCount() : Int = Avatar.values().size

    override fun onBindViewHolder(p0 : AvatarViewHolder, p1 : Int) {
        p0.bind(Avatar.values()[p1].drawableId)
    }

    inner class AvatarViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val imageView : AvatarView = view.iv_avatar_view_holder_avatar

        fun bind(imageId : Int) {
            imageView.clipToOutline = true
            imageView.setImageResource(imageId)
        }
    }
}