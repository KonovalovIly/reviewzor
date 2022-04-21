package ru.ssau.reviewzor.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.BookmarkRepository
import ru.ssau.reviewzor.domain.entity.PlacesModel

class ListViewModel(private val repository: BookmarkRepository) : ViewModel() {

    var listPlaces = repository.allBookmarks

    fun update(bookmark: PlacesModel) {
        viewModelScope.launch {
            repository.updateBookmark(bookmark)
        }
    }
}