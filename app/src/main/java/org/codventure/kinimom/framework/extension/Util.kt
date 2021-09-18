package org.codventure.kinimom.framework.extension

import org.codventure.kinimom.R

fun String.removeChars(): String {
    var res = ""
    for (c in this)
        if (c.isDigit())
            res += c
    return res
}

fun String?.userAvatar(): Int {
    if (this == null || this.isBlank()) return R.drawable.community_profile_img_01
    return when (this.first().toInt() % 10) {
        1 -> R.drawable.community_profile_img_01
        2 -> R.drawable.community_profile_img_02
        3 -> R.drawable.community_profile_img_03
        4 -> R.drawable.community_profile_img_04
        5 -> R.drawable.community_profile_img_05
        6 -> R.drawable.community_profile_img_06
        7 -> R.drawable.community_profile_img_07
        8 -> R.drawable.community_profile_img_08
        9 -> R.drawable.community_profile_img_09
        else -> R.drawable.community_profile_img_10
    }
}

fun Int.addZeroIfNotDoubleDigits(): String{
    if (-10 < this && this < 10)
        return "0$this"
    return this.toString()
}