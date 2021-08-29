package org.codventure.kinimom.ui.main.tabs.community

import org.codventure.kinimom.core.domain.Community

/**
 * Created by abduaziz on 8/29/21 at 11:56 PM.
 */

interface CommunityView {
    fun showCommunities(communities: List<Community>)
    fun showCommunitiesFetchError()
}