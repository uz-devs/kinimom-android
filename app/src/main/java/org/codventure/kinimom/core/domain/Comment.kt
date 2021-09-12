package org.codventure.kinimom.core.domain

class Comment(
    var id: String?,
    var nickname: String?,
    var icon: String?,
    var profile_image: String?,
    var community_id: String?,
    var user_id: String?,
    var comment: String?,
    var like_qty: String?,
    var like_it: String?,
    var created_at: String?,
    var updated_at: String?,


    var isOwnedByUser: Boolean = false
){
    fun userLikesIt() = like_it != "FALSE"
}