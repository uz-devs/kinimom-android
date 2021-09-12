package org.codventure.kinimom.ui.main.tabs.community.detail

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Comment
import org.codventure.kinimom.framework.extension.userAvatar

/**
 * Created by abduaziz on 9/12/21 at 7:42 PM.
 */

class CommentsAdapter(val comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivCommentUserImage = itemView.findViewById<ImageView>(R.id.ivCommentUserImage)
        private val tvCommentNickname = itemView.findViewById<TextView>(R.id.tvCommentNickname)
        private val ivCommentNew = itemView.findViewById<ImageView>(R.id.ivCommentNew)
        private val ivCommentMenu = itemView.findViewById<ImageView>(R.id.ivCommentMenu)
        private val tvCommentDate = itemView.findViewById<TextView>(R.id.tvCommentDate)

        private val tvCommentText = itemView.findViewById<TextView>(R.id.tvCommentText)

        private val llCommentLike = itemView.findViewById<LinearLayout>(R.id.llCommentLike)
        private val ivCommentLike = itemView.findViewById<ImageView>(R.id.ivCommentLike)
        private val tvCommentLikeCount = itemView.findViewById<TextView>(R.id.tvCommentLikeCount)

        fun bind(comment: Comment) {
            setProfileImage(comment.profile_image, comment.nickname.userAvatar())

            tvCommentNickname.text = comment.nickname
            tvCommentDate.text = comment.updated_at

            ivCommentMenu.visibility = if (comment.isOwnedByUser) View.VISIBLE else View.GONE

            tvCommentText.text = comment.comment?.trim() ?: ""
            tvCommentLikeCount.text = comment.like_qty
        }

        private fun setProfileImage(url: String?, userAvatar: Int) {
            if (url.isNullOrBlank()){
                ivCommentUserImage.setImageResource(userAvatar)
                return
            }
            Glide.with(itemView.context).load(url).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    ivCommentUserImage.setImageResource(userAvatar)
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
            }).into(ivCommentLike)
        }
    }

}