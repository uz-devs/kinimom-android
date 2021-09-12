package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.Comment

/**
 * Created by abduaziz on 9/12/21 at 9:16 PM.
 */

class CommentResponse(
    val status: Boolean?,
    var data: Comment?
)