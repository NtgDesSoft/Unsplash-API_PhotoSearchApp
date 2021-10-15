package com.nelson.photosearchapp.ui.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nelson.photosearchapp.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {

    private val _currentQuery = MutableLiveData<String>()

    val photos = _currentQuery.switchMap { newAuthor ->
        unsplashRepository.fetchPhotosByAuthor(newAuthor)
    }.cachedIn(viewModelScope)

    fun searchPhotosByAuthor(username: String) {
        _currentQuery.value = username
    }

}