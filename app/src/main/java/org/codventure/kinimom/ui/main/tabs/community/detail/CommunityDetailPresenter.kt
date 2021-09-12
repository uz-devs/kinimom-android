package org.codventure.kinimom.ui.main.tabs.community.detail

import org.codventure.kinimom.core.interactors.GetCommunity
import org.codventure.kinimom.core.interactors.SendComment
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import javax.inject.Inject

class CommunityDetailPresenter(val view: CommunityDetailView) {
    @Inject
    lateinit var getCommunity: GetCommunity

    @Inject
    lateinit var sendComment: SendComment

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

    fun comment(communityId: Long, commentText: String){
        if (commentText.isBlank()) return
        doAsync {
            val comment = sendComment(communityId, commentText)
            uiThread {
                if (comment == null)
                    view.showSendCommentError()
                else
                    view.addNewComment(comment)
            }
        }
    }

}