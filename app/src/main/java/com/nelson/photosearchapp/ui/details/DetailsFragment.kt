package com.nelson.photosearchapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nelson.photosearchapp.R
import com.nelson.photosearchapp.databinding.FragmentDetailsBinding
import com.nelson.photosearchapp.ui.details.adapter.DetailsAdapter
import com.nelson.photosearchapp.ui.details.viewmodel.DetailsViewModel
import com.nelson.photosearchapp.ui.gallery.adapter.ReloadErrorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val detailsViewModel by viewModels<DetailsViewModel>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding ?: throw(IllegalAccessException())

    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var detailsAdapter: DetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerView()
        setupObserver()
        detailsViewModel.searchPhotosByAuthor(args.results.user.username)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        val photo = args.results

        binding.apply {
            authorTextview.text = photo.user.name
            descriptionTextview.text = photo.description
            morePhotosTextview.text = getString(R.string.more_photos_from_x, photo.user.name)

            Glide.with(root)
                .load(photo.urls.full)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoImageview)
        }
    }

    private fun setupRecyclerView() {
        detailsAdapter = DetailsAdapter()
        binding.detailsRecyclerview.adapter = detailsAdapter
            .withLoadStateHeaderAndFooter(
                ReloadErrorAdapter { detailsAdapter.retry() },
                ReloadErrorAdapter { detailsAdapter.retry() }
            )
    }

    private fun setupObserver() {
        detailsViewModel.photos.observe(viewLifecycleOwner) {
           lifecycleScope.launch {
               detailsAdapter.submitData(it)
           }
        }
    }
}