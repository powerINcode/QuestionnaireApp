package com.powerincode.questionnaire_app.screens.auth.login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.views.AvatarView
import com.powerincode.questionnaire_app.core.views.recyclerview.BaseRecyclerView
import kotlinx.android.synthetic.main.item_avatar_list.view.*

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AvatarRecyclerViewAdapter : BaseRecyclerView<Int, BaseRecyclerView.OnItemClick<Int>>() {
    override fun onCreateViewHolder(p0 : ViewGroup, p1 : Int) : BaseViewHolder {
        return AvatarViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_avatar_list, p0, false))
    }


    inner class AvatarViewHolder(view : View) : BaseViewHolder(view) {
        private val imageView : AvatarView = view.iv_avatar_view_holder_avatar

        override fun bind(item : Int) {
            super.bind(item)

            imageView.clipToOutline = true
            imageView.setImageResource(item)
        }

        override fun onSelectionChange(isSelected : Boolean) {
            super.onSelectionChange(isSelected)

            imageView.isChecked = isSelected
        }
    }
}