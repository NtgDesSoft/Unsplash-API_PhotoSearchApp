package com.nelson.photosearchapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nelson.photosearchapp.domain.authored.AuthoredPhotoResponse
import com.nelson.photosearchapp.network.PhotoApi
import retrofit2.HttpException
import java.io.IOException

private const val INITIAL_LOAD_KEY = 1

class AuthoredPhotoPagingSource(
    private val username: String,
    private val photoApi: PhotoApi
) : PagingSource<Int, AuthoredPhotoResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuthoredPhotoResponse> {
        return try {
            val position = params.key ?: INITIAL_LOAD_KEY

            val response = photoApi.getAuthoredPhotos(
                username,
                page = position,
                perPage = params.loadSize
            )

            LoadResult.Page(
                data = response,
                prevKey = if (position == INITIAL_LOAD_KEY) null else position - 1,
                nextKey = if (response.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AuthoredPhotoResponse>): Int? = null
}