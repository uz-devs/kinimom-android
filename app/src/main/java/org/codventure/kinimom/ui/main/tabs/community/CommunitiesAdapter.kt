package org.codventure.kinimom.ui.main.tabs.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Community

/**
 * Created by abduaziz on 8/29/21 at 11:46 PM.
 */

class CommunitiesAdapter(private val communities: List<Community>) :
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

        private val ivCommunityUserImage = itemView.findViewById<ImageView>(R.id.ivCommunityUserImage)
        private val tvNickname = itemView.findViewById<TextView>(R.id.tvCommunityUserNickname)
        private val tvDate = itemView.findViewById<TextView>(R.id.rvCommunityDate)
        private val tvContent = itemView.findViewById<TextView>(R.id.tvCommunityContent)
        private val tvCommunityReadMore = itemView.findViewById<TextView>(R.id.tvCommunityReadMore)

        private val tvCommentQuantity = itemView.findViewById<TextView>(R.id.tvCommunityCommentQuantity)
        private val tvLikeQuantity = itemView.findViewById<TextView>(R.id.tvCommunityLikeQuantity)
        private val ivLikeIt = itemView.findViewById<ImageView>(R.id.ivLikeIt)

        fun bind(community: Community) {
            // todo: bind data
            tvNickname.text = community.nickname ?: ""
            tvDate.text = community.date ?: ""
            tvContent.text = community.content ?: ""

            tvCommentQuantity.text = community.comment_qty ?: ""
            tvLikeQuantity.text = community.like_qty ?: ""

            if (community.userLikesIt())
                ivLikeIt.setImageResource(R.drawable.community_ic_good_on)
            else
                ivLikeIt.setImageResource(R.drawable.community_ic_good_off)
        }
    }

}