package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.CommentRequest
import org.codventure.kinimom.framework.settings.Settings
import javax.inject.Inject

/**
 * Created by abduaziz on 9/12/21 at 9:18 PM.
 */

class SendComment
@Inject constructor(
    private val repository: KinimomRepository,
    private val settings: Settings
) {
    operator fun invoke(communityId: Long, commentText: String) =
        repository.comment(CommentRequest(communityId, settings.getUserId(), commentText))
}