package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.Comment
import org.codventure.kinimom.core.domain.Community

class CommunityDetailResponse(
    val community_one: Community?,
    val comments: ArrayList<Comment>?
)