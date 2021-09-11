package org.codventure.kinimom.ui.main.tabs.community.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_community_detail.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.extension.userAvatar
import org.codventure.kinimom.ui.main.tabs.community.CommunityGalleryAdapter

/**
 * Created by abduaziz on 9/12/21 at 7:37 AM.
 */

class CommunityDetailFragment(private val communityOffline: Community) :
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
    }

    override fun showLoadingError() {
        showCommunity(communityOffline)
    }

}