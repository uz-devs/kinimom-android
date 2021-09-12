package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.BasicRequest
import javax.inject.Inject

class GetTestLastOne
@Inject constructor(private val kinimomRepository: KinimomRepository) {
    operator fun invoke(userId: Long) = kinimomRepository.getTestLastOne(BasicRequest(user_id = userId))
}