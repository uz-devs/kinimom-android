package org.codventure.kinimom.ui.main.tabs.community

import org.codventure.kinimom.core.domain.Community

interface CommunityView {
    fun showCommunities(communities: List<Community>)
    fun showCommunitiesFetchError()
}