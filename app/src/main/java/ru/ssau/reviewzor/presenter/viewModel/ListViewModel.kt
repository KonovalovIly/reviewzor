package ru.ssau.reviewzor.presenter.viewModel

import androidx.lifecycle.ViewModel
import ru.ssau.reviewzor.data.BookmarkRepository

class ListViewModel(repository: BookmarkRepository): ViewModel() {

    var listPlaces = repository.allBookmarks
}