package org.codventure.kinimom.ui.main.tabs.community

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import me.relex.circleindicator.CircleIndicator3
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.framework.extension.userAvatar

/**
 * Created by abduaziz on 8/29/21 at 11:46 PM.
 */

class CommunitiesAdapter(private val communities: List<Community>,
                         private val onClickCommunity: (community: Community, wannaComment: Boolean) -> Unit) :
    RecyclerView.Adapter<CommunitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(communities[position])
    }

    override fun getItemCount(): Int {
        return communities.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivCommunityUserImage =
            itemView.findViewById<ImageView>(R.id.ivCommunityUserImage)
        private val tvNickname = itemView.findViewById<TextView>(R.id.tvCommunityUserNickname)
        private val ivCommunityMenu = itemView.findViewById<ImageView>(R.id.ivCommunityMenu)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvCommunityDate)


        private val viewPager2Parent = itemView.findViewById<FrameLayout>(R.id.communityViewPagerParent)
        private val viewPager2 = itemView.findViewById<ViewPager2>(R.id.communityViewPager)
        private val circleIndicator = itemView.findViewById<CircleIndicator3>(R.id.circleIndicator)
        private val tvCommunityGalleryImageCount = itemView.findViewById<TextView>(R.id.tvCommunityGalleryImageCount)

        private val tvCommunityContent = itemView.findViewById<TextView>(R.id.tvCommunityContent)
        private val tvCommunitySeeMore = itemView.findViewById<TextView>(R.id.tvCommunitySeeMore)

        private val tvCommentQuantity =
            itemView.findViewById<TextView>(R.id.tvCommunityCommentCount)
        private val tvLikeQuantity = itemView.findViewById<TextView>(R.id.tvCommunityLikeCount)

        private val ivLikeIt = itemView.findViewById<ImageView>(R.id.ivCommunityLikeIt)
        private val tvLikeIt = itemView.findViewById<TextView>(R.id.tvCommunityLike)

        private val llCommunityComment = itemView.findViewById<LinearLayout>(R.id.llCommunityComment)
        private val ivCommunityComment = itemView.findViewById<ImageView>(R.id.ivCommunityComment)
        private val tvCommunityComment = itemView.findViewById<TextView>(R.id.tvCommunityComment)

        fun bind(community: Community) {
            itemView.setOnClickListener {
                onClickCommunity(community, false)
            }

            llCommunityComment.setOnClickListener {
                onClickCommunity(community, true)
            }


            setProfileImage(community.profile_image, community.nickname.userAvatar())
            ivCommunityMenu.visibility = if (community.isOwnedByUser) View.VISIBLE else View.GONE

            tvNickname.text = community.nickname ?: ""
            tvDate.text = community.date ?: ""

            val imageUrls = community.images()
            if (imageUrls.isEmpty()) {
                viewPager2Parent.visibility = View.GONE
            } else {
                viewPager2Parent.visibility = View.VISIBLE
                viewPager2.adapter = CommunityGalleryAdapter(imageUrls)
                circleIndicator.setViewPager(viewPager2)
                if (imageUrls.size == 1){
                    tvCommunityGalleryImageCount.visibility = View.GONE
                    circleIndicator.visibility = View.GONE
                }else{
                    tvCommunityGalleryImageCount.visibility = View.VISIBLE
                    circleIndicator.visibility = View.VISIBLE
                }
                viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        tvCommunityGalleryImageCount.text = " ${position+1} / ${imageUrls.size}"
                    }
                })
            }

            val content = community.content?.trim() ?: ""
            tvCommunityContent.text = content
            tvCommunityContent.visibility = if (content.isBlank()) View.GONE else View.VISIBLE
            tvCommunityContent.post {
                tvCommunitySeeMore.visibility =
                    if (tvCommunityContent.lineCount > 3) View.VISIBLE else View.GONE
            }
            val seeMoreListener = object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (tvCommunityContent.maxLines <= 3) {
                        tvCommunityContent.maxLines = Int.MAX_VALUE
                        tvCommunitySeeMore.setText(itemView.context.getString(R.string.see_less))
                    } else {
                        tvCommunityContent.maxLines = 3
                        tvCommunitySeeMore.setText(itemView.context.getString(R.string.see_more))
                    }
                }
            }
            tvCommunitySeeMore.setOnClickListener(seeMoreListener)
            tvCommunityContent.setOnClickListener(seeMoreListener)

            tvCommentQuantity.text = community.comment_qty ?: ""
            tvLikeQuantity.text = community.like_qty ?: ""

            if (community.userLikesIt()) {
                ivLikeIt.setImageResource(R.drawable.community_ic_good_on)
                tvLikeIt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            } else {
                ivLikeIt.setImageResource(R.drawable.community_ic_good_off)
                tvLikeIt.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
            }
        }

        private fun setProfileImage(url: String?, userAvatar: Int){
            if (url.isNullOrBlank()){
                ivCommunityUserImage.setImageResource(userAvatar)
                return
            }
            Glide.with(itemView.context).load(url).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    ivCommunityUserImage.setImageResource(userAvatar)
                    return true
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(ivCommunityUserImage)
        }
    }

}