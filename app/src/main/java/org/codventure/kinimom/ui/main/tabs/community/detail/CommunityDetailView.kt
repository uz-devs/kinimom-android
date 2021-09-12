package org.codventure.kinimom.ui.main.tabs.community.detail

import org.codventure.kinimom.core.domain.Community

/**
 * Created by abduaziz on 9/12/21 at 7:54 AM.
 */

interface CommunityDetailView {

    fun showCommunity(community: Community)
    fun showLoadingError()

}