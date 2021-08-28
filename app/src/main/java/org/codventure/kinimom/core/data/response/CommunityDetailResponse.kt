package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.Comment
import org.codventure.kinimom.core.domain.Community

/**
 * Created by abduaziz on 8/28/21 at 12:41 AM.
 */

class CommunityDetailResponse(
    val community_one: Community?,
    val comments: ArrayList<Comment>?
)