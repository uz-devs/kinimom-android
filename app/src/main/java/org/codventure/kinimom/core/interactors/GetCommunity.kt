package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.CommunityDetailRequest
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.framework.settings.Settings
import javax.inject.Inject

class GetCommunity
@Inject constructor(private val repository: KinimomRepository, private val settings: Settings) {
    operator fun invoke(community_id: Long): Community? {
        return repository.getCommunity(CommunityDetailRequest(settings.getUserId(), community_id))
    }
}