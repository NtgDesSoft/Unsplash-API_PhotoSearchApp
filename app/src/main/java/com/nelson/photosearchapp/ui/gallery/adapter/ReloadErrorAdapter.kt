package com.nelson.photosearchapp.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nelson.photosearchapp.databinding.ItemReloadErrorBinding

class ReloadErrorAdapter(val retryClick: () -> Unit) :
    LoadStateAdapter<ReloadErrorAdapter.ReloadErrorViewHolder>() {

    inner class ReloadErrorViewHolder(binding: ItemReloadErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retryClick()
            }
        }

        fun bind(loadState: LoadState) {}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReloadErrorViewHolder {
        val binding = ItemReloadErrorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReloadErrorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReloadErrorViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}