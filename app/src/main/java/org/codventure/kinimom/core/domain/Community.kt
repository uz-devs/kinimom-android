package org.codventure.kinimom.core.domain

/**
 * Created by abduaziz on 8/28/21 at 12:12 AM.
 */

class Community(
    val id: String,
    val user_id: String?,
    val icon: String?,
    val nickname: String?,
    val profile_image: String?,
    val content: String?,
    val image_1_url: String?,
    val image_2_url: String?,
    val image_3_url: String?,
    val image_4_url: String?,
    val image_5_url: String?,
    val image_ratio1: String?,
    val image_ratio2: String?,
    val image_ratio3: String?,
    val image_ratio4: String?,
    val image_ratio5: String?,
    val comment_qty: String?,
    val like_qty: String?,
    val date: String?,
    val created_at: String?,
    val updated_at: String?,
    val like_it: String?,

    // comments are not included in real JSON response, binded later, see framework/network/Network.kt
    var comments: ArrayList<Comment> = arrayListOf()
)