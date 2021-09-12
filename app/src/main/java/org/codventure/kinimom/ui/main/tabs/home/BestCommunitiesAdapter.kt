package org.codventure.kinimom.ui.main.tabs.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Community
import java.util.*

class BestCommunitiesAdapter(private val communities: List<Community>) : RecyclerView.Adapter<BestCommunitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_community_small, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(communities[position])
    }

    override fun getItemCount(): Int {
        return communities.size
    }

    inner class ViewHolder(x: View) : RecyclerView.ViewHolder(x) {
        val ivImg = x.findViewById<ImageView>(R.id.ivCommunityUserImage)
        val tvName = x.findViewById<TextView>(R.id.tvCommunityUserNickname)
        val tvContent = x.findViewById<TextView>(R.id.tvCommunityContent)

        fun bind(community: Community) {
            val imgUrls = mutableListOf(community.image_1_url, community.image_2_url, community.image_3_url, community.image_4_url, community.image_5_url)
            while (imgUrls.contains(null))
                imgUrls.remove(null)
            if (imgUrls.isNotEmpty())
                Glide.with(itemView.context).load(imgUrls[Random().nextInt(imgUrls.count())]).into(ivImg)
            else
                ivImg.setImageResource(R.drawable.home_sample_img_best_01)

            tvName.text = community.nickname
            tvContent.text = community.content?.trim()
        }
    }
}
