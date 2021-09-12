package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.GetMenstruationRequest
import javax.inject.Inject

class GetMenstruation
@Inject constructor(private val kinimomRepository: KinimomRepository) {
    operator fun invoke(userId: Long, month: String) = kinimomRepository.getMenstruation(GetMenstruationRequest(user_id = userId, month = month))
}