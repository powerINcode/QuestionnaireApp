package com.powerincode.questionnaire_app.core.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Checkable
import android.widget.ImageView
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.common.toDp

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AvatarView @JvmOverloads constructor(
    context : Context, attrs : AttributeSet? = null, defStyleAttr : Int = 0
) : ImageView(context, attrs, defStyleAttr), Checkable {

    private val selectionPaint : Paint

    init {
        selectionPaint = Paint().apply {
            strokeWidth = 5.toDp()
            style = Paint.Style.STROKE
            val typeValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorAccent, typeValue, true)
            color = typeValue.data
        }
    }

    private var isAvatarChecked : Boolean = false
    set(value) {
        field = value

        invalidate()
    }
    override fun isChecked() : Boolean = isAvatarChecked

    override fun toggle() {
        isAvatarChecked = !isAvatarChecked
    }

    override fun setChecked(checked : Boolean) {
        isAvatarChecked = checked
    }

    init {
        clipToOutline = true
    }

    override fun onDraw(canvas : Canvas) {
        super.onDraw(canvas)

        if (isAvatarChecked) {
            val size = Math.min(width, height).toFloat()
            canvas.drawCircle(size / 2, size / 2, size / 2, selectionPaint)
        }
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