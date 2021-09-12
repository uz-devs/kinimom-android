package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.BasicRequest
import org.codventure.kinimom.core.data.request.CheckNicknameRequest
import org.codventure.kinimom.core.data.response.GetAllNoticeResponse
import javax.inject.Inject

class GetAllNotice
@Inject constructor(private val repository: KinimomRepository) {
    operator fun invoke(user_id: Long): GetAllNoticeResponse? {
        return repository.getAllNotice(BasicRequest(user_id = user_id))
    }
}
