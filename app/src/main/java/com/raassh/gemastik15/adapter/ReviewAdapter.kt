package com.raassh.gemastik15.adapter

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raassh.gemastik15.R
import com.raassh.gemastik15.api.response.FacilitiesItem
import com.raassh.gemastik15.api.response.FacilityQualityItem
import com.raassh.gemastik15.api.response.ReviewData
import com.raassh.gemastik15.databinding.ReviewCompactItemBinding
import com.raassh.gemastik15.databinding.ReviewItemBinding
import com.raassh.gemastik15.local.db.PlaceEntity
import com.raassh.gemastik15.utils.*
import com.raassh.gemastik15.utils.LinearSpaceItemDecoration
import com.raassh.gemastik15.utils.loadImage

class ReviewAdapter(private val isCompact: Boolean = false) : ListAdapter<ReviewData, ReviewAdapter.ReviewViewHolder>(DIFF_CALLBACK) {
    var onItemClickListener: ((ReviewData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ReviewViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        if (!isCompact) {
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            view.layoutParams = ViewGroup.LayoutParams(
                convertDpToPixel(360, parent.context).toInt(),
                convertDpToPixel(212, parent.context).toInt()
            )
        }
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReviewItemBinding.bind(itemView)
        private val context = itemView.context

        init {
            binding.rvReviewFacilities.addItemDecoration(LinearSpaceItemDecoration(16, RecyclerView.HORIZONTAL))
        }

        fun bind(review: ReviewData) {
            binding.apply {
                val facilities: List<FacilityQualityItem>
                if (isCompact) {
                    btnReviewReport.visibility = View.GONE
                    facilities = review.facilities.take(5)
                    tvReviewText.maxLines = 4
                    tvReviewText.ellipsize = TextUtils.TruncateAt.END
                }
                else {
                    btnReviewReport.visibility = View.VISIBLE
                    facilities = review.facilities
                    btnReviewReport.setOnClickListener {
                        onItemClickListener?.invoke(getItem(adapterPosition))
                    }
                }

                imgReviewProfile.loadImage(review.user.profilePicture)
                tvReviewName.text = review.user.name
                tvReviewText.text = review.review
                rvReviewFacilities.adapter = ReviewFacilitiesAdapter().apply {
                    submitList(facilities)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewData>() {
            override fun areItemsTheSame(oldItem: ReviewData, newItem: ReviewData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ReviewData, newItem: ReviewData): Boolean {
                return oldItem == newItem
            }
        }
    }
}