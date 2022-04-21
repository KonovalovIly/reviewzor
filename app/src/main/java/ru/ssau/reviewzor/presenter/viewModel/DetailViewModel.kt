package ru.ssau.reviewzor.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.BookmarkRepository
import ru.ssau.reviewzor.domain.entity.PlacesModel

class DetailViewModel(private val repository: BookmarkRepository) : ViewModel() {

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

}