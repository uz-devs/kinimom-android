package org.codventure.kinimom.ui.main.tabs.community.detail

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_community_detail.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Comment
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.extension.toast
import org.codventure.kinimom.framework.extension.userAvatar
import org.codventure.kinimom.ui.MainActivity


/**
 * Created by abduaziz on 9/12/21 at 7:37 AM.
 */

class CommunityDetailFragment(private val communityOffline: Community,
private val wannaComment: Boolean) :
    Fragment(R.layout.fragment_community_detail), CommunityDetailView {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    lateinit var presenter: CommunityDetailPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CommunityDetailPresenter(this)
        appComponent.inject(presenter)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        presenter.loadCommunityId(communityOffline.id.toLong())

        tvComment.setOnClickListener {
            presenter.comment(communityOffline.id.toLong(), etComment.text.toString())
        }

        if (wannaComment){
            (activity as MainActivity).showSoftKeyboard(etComment)
        }
    }

    override fun showCommunity(community: Community) {
        ivCommunityDetailUserImage.setImageResource(community.nickname.userAvatar())
        tvCommunityDetailUserNickname.text = community.nickname
        tvCommunityDetailDate.text = community.date

        val images = community.images()
        if (images.isEmpty()) {
            rvCommunityDetailImages.visibility = View.GONE
        } else {
            rvCommunityDetailImages.visibility = View.VISIBLE
            rvCommunityDetailImages.layoutManager = LinearLayoutManager(requireContext())
            rvCommunityDetailImages.adapter = CommunityGalleryVerticalAdapter(images)
        }

        tvCommunityDetailContent.text = community.content?.trim()
        tvCommunityDetailLikeCount.text = "${community.like_qty} 명이 좋아합니다."

        tvCommentsCount.text = getString(R.string.comments) + "(${community.comment_qty})"
        if (community.comments.isEmpty())
            tvCommentsCount.text = getString(R.string.no_comments)

        rvComments.layoutManager = LinearLayoutManager(requireContext())
        rvComments.adapter = CommentsAdapter(community.comments)
    }

    override fun showLoadingError() {
        showCommunity(communityOffline)
        toast(getString(R.string.error_internet_connection))
    }

    override fun addNewComment(comment: Comment) {
        etComment.setText("")
        presenter.loadCommunityId(communityOffline.id.toLong())
        (activity as MainActivity).hideSoftInput()
    }

    override fun showSendCommentError() {
        toast(getString(R.string.error_sending_comment))
    }
}