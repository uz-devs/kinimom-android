package org.codventure.kinimom.ui.main.tabs.community

import org.codventure.kinimom.core.interactors.GetCommunities
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import javax.inject.Inject

class CommunityPresenter(val view: CommunityView) {

    @Inject
    lateinit var getCommunities: GetCommunities

    fun fetchCommunities() {
        doAsync {
            val communityList = getCommunities()
            uiThread {
                if (communityList == null) {
                    view.showCommunitiesFetchError()
                    return@uiThread
                }

                view.showCommunities(communityList)
            }
        }
    }

}