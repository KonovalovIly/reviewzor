package ru.ssau.reviewzor.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.BookmarkRepository
import ru.ssau.reviewzor.data.NetworkRepository
import ru.ssau.reviewzor.data.TokenRepository
import ru.ssau.reviewzor.domain.entity.PlacesModel
import java.io.File

class DetailViewModel(
    private val repository: BookmarkRepository,
    private val networkRepository: NetworkRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _bookmark = MutableLiveData<PlacesModel>()
    val bookmark: LiveData<PlacesModel> = _bookmark

    fun sendResponse(name: String) {
        viewModelScope.launch {
            runCatching {
                repository.getNotes(name)
            }.onSuccess {
                _bookmark.value = it
            }
        }
    }

    fun update(
        name: String,
        address: String,
        category: String,
        detail: String,
        rating: Double
    ) {
        val bookmark = _bookmark.value
        if (bookmark != null) {
            viewModelScope.launch {
                repository.updateBookmark(
                    PlacesModel(
                        id = bookmark.id,
                        name = name,
                        address = address,
                        category = category,
                        latitude = bookmark.latitude,
                        longitude = bookmark.longitude,
                        follow = bookmark.follow,
                        detail = detail,
                        rating = rating,
                        image = bookmark.image
                    )
                )
            }
        }
    }

    fun update(
        name: String,
        address: String,
        category: String,
        detail: String,
        rating: Double,
        image: String
    ) {
        val bookmark = _bookmark.value
        if (bookmark != null) {
            viewModelScope.launch {
                repository.updateBookmark(
                    PlacesModel(
                        id = bookmark.id,
                        name = name,
                        address = address,
                        category = category,
                        latitude = bookmark.latitude,
                        longitude = bookmark.longitude,
                        follow = bookmark.follow,
                        detail = detail,
                        rating = rating,
                        image = image
                    )
                )
            }
        }
    }

    fun uploadPhoto(file: File) {
        viewModelScope.launch {
            kotlin.runCatching { networkRepository.uploadPhoto(file,tokenRepository.getToken())
            }.onSuccess {
                Log.d("Logger", it)
            }.onFailure {
                Log.d("Logger", it.message.toString())
            }
        }
    }

}