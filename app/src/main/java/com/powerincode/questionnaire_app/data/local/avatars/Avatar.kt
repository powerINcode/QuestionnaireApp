package com.powerincode.questionnaire_app.data.local.avatars

import android.support.annotation.DrawableRes
import com.powerincode.questionnaire_app.R

/**
 * Created by powerman23rus on 25/02/2019.
 */
enum class Avatar(@DrawableRes val drawableId : Int) {

    ONE(R.drawable.avatar_1_raster),
    TWO(R.drawable.avatar_2_raster),
    THREE(R.drawable.avatar_3_raster),
    FOUR(R.drawable.avatar_4_raster),
    FIVE(R.drawable.avatar_5_raster),
    SIX(R.drawable.avatar_6_raster),
    SEVEN(R.drawable.avatar_7_raster),
    EIGHT(R.drawable.avatar_8_raster),
    NINE(R.drawable.avatar_9_raster),
    TEN(R.drawable.avatar_10_raster),
    ELEVEN(R.drawable.avatar_11_raster),
    TWELVE(R.drawable.avatar_12_raster),
    THIRTEEN(R.drawable.avatar_13_raster),
    FOURTEEN(R.drawable.avatar_14_raster),
    FIFTEEN(R.drawable.avatar_15_raster),
    SIXTEEN(R.drawable.avatar_16_raster);

    val nameForAccessibility get() = "Avatar ${ordinal + 1}"
}