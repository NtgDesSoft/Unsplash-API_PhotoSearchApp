package com.nelson.photosearchapp.ui.gallery

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nelson.photosearchapp.R
import com.nelson.photosearchapp.databinding.FragmentGalleryBinding
import com.nelson.photosearchapp.domain.Results
import com.nelson.photosearchapp.ui.gallery.adapter.GalleryAdapter
import com.nelson.photosearchapp.ui.gallery.adapter.ReloadErrorAdapter
import com.nelson.photosearchapp.ui.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val galleryViewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding get() = _binding ?: throw(IllegalAccessException())

    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchIcon = menu.findItem(R.id.search_icon)
        val searchView = searchIcon.actionView as SearchView

        searchView.queryHint = "Enter a search query"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) galleryViewModel.searchPhotos(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean { return true }
        })

        return
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter { navigateToDetails(it) }
        binding.galleryRecyclerView.adapter = galleryAdapter
            .withLoadStateHeaderAndFooter(
                ReloadErrorAdapter { galleryAdapter.retry() },
                ReloadErrorAdapter { galleryAdapter.retry() }
            )
    }

    private fun setupObserver() {
        galleryViewModel.photos.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                galleryAdapter.submitData(it)
            }
        }
    }

    private fun navigateToDetails(results: Results) {
        findNavController().navigate(
            GalleryFragmentDirections.toDetailsFragment(results)
        )
    }

}