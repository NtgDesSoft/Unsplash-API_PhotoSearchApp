package com.nelson.photosearchapp.ui.gallery.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.nelson.photosearchapp.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "Kittens"
    }

    private val _currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = _currentQuery.switchMap { newQuery ->
            unsplashRepository.fetchPhotos(newQuery)
    }.cachedIn(viewModelScope)

    fun searchPhotos(query: String) {
        _currentQuery.value = query
    }


}