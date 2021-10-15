package com.nelson.photosearchapp.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nelson.photosearchapp.databinding.ItemPhotoBinding
import com.nelson.photosearchapp.domain.Results

class GalleryAdapter(val photoClick: (Results) -> Unit) :
    PagingDataAdapter<Results, GalleryAdapter.GalleryViewHolder>(DIFF_CALLBACK) {

    inner class GalleryViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { photoClick(it) } }
            }


        fun bind(results: Results) {
            binding.authorTextview.text = results.user.name

            Glide.with(itemView)
                .load(results.urls.regular)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.photoImageview)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        getItem(position)?.apply { holder.bind(this) }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem == newItem
}