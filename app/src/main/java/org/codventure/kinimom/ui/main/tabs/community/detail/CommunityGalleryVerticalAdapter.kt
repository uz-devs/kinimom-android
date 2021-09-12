package org.codventure.kinimom.ui.main.tabs.community.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.codventure.kinimom.R

/**
 * Created by abduaziz on 9/12/21 at 5:17 AM.
 */

class CommunityGalleryVerticalAdapter(private val images: List<String>) :
    RecyclerView.Adapter<CommunityGalleryVerticalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_vertical, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iv = itemView.findViewById<ImageView>(R.id.ivCommunityGallery)

        fun bind(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(iv)
        }
    }
}