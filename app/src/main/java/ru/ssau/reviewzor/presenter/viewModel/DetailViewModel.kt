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

    val imageUrl = MutableLiveData<String>("")

    fun sendResponse(name: String) {
        viewModelScope.launch {
            runCatching {
                repository.getNotes(name)
            }.onSuccess {
                imageUrl.value = it?.image
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
                imageUrl.value?.let {
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
                        image = it
                    )
                }?.let {
                    repository.updateBookmark(
                        it
                    )
                }
            }
        }
    }

    fun uploadPhoto(file: File) {
        viewModelScope.launch {
            kotlin.runCatching {
                networkRepository.uploadPhoto(file, tokenRepository.getToken())
            }.onSuccess {
                Log.d("Logger", it)
                imageUrl.value = "https://reviewzor-api.herokuapp.com/images/$it"
            }.onFailure {
                Log.d("Logger", it.message.toString())
            }
        }
    }

}