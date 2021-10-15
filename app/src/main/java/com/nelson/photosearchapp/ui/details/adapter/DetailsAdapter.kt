package com.nelson.photosearchapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nelson.photosearchapp.databinding.ItemAuthoredPhotoBinding
import com.nelson.photosearchapp.domain.authored.AuthoredPhotoResponse

class DetailsAdapter :
    PagingDataAdapter<AuthoredPhotoResponse, DetailsAdapter.DetailsViewHolder>(DIFF_CALLBACK) {

    inner class DetailsViewHolder(private val binding: ItemAuthoredPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: AuthoredPhotoResponse) {
            Glide.with(itemView)
                .load(photo.urls.regular)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.photoImageview)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val binding = ItemAuthoredPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        getItem(position)?.apply { holder.bind(this) }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AuthoredPhotoResponse>() {
    override fun areItemsTheSame(
        oldItem: AuthoredPhotoResponse,
        newItem: AuthoredPhotoResponse
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: AuthoredPhotoResponse,
        newItem: AuthoredPhotoResponse
    ): Boolean =
        oldItem == newItem
}