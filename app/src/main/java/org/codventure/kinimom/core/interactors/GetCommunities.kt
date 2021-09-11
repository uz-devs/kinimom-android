package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.CommunityListRequest
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.framework.settings.Settings
import javax.inject.Inject

/**
 * Created by abduaziz on 8/28/21 at 12:26 AM.
 */

class GetCommunities
@Inject constructor(private val repository: KinimomRepository,
                    private val settings: Settings){
    operator fun invoke(offset: Int = 0): ArrayList<Community>?{
        return repository.getCommunityList(CommunityListRequest(settings.getUserId(), offset))
    }
}