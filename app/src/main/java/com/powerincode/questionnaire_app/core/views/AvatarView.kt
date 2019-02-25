package com.powerincode.questionnaire_app.core.views

import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AvatarView @JvmOverloads constructor(
    context : Context, attrs : AttributeSet? = null, defStyleAttr : Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    init {
        clipToOutline = true
    }

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (w > 0 && h > 0) {
            outlineProvider = RoundOutlineProvider(Math.min(w, h))
        }
    }

    private class RoundOutlineProvider(val size : Int) : ViewOutlineProvider() {
        override fun getOutline(view : View?, outline : Outline) {
            outline.setOval(Rect(0, 0, size, size))
        }
    }
}