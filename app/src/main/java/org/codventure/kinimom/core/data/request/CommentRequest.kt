package org.codventure.kinimom.core.data.request

/**
 * Created by abduaziz on 9/12/21 at 9:12 PM.
 */

class CommentRequest(
    val community_id: Long,
    val user_id: Long,
    val comment: String
)