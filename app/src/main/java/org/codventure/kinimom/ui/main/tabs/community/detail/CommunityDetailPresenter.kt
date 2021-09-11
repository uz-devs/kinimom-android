package org.codventure.kinimom.ui.main.tabs.community.detail

import android.util.Log
import org.codventure.kinimom.core.interactors.GetCommunity
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 9/12/21 at 7:55 AM.
 */

class CommunityDetailPresenter(val view: CommunityDetailView) {

    @Inject
    lateinit var getCommunity: GetCommunity

    fun loadCommunityId(communityId: Long) {
        doAsync {
            val response = getCommunity(communityId)
            uiThread {
                if (response != null)
                    view.showCommunity(response)
                else
                    view.showLoadingError()
            }
        }
    }

}