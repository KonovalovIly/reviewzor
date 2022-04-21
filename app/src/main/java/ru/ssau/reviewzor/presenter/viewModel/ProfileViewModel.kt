package ru.ssau.reviewzor.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.BookmarkRepository
import ru.ssau.reviewzor.domain.entity.PlacesModel
import ru.ssau.reviewzor.domain.entity.ProfileModel

class ProfileViewModel(private val repository: BookmarkRepository) : ViewModel() {

    var favoritesPlaces = repository.favoritesPlaces

    var profile = repository.profile

    fun update(bookmark: PlacesModel) {
        viewModelScope.launch {
            repository.updateBookmark(bookmark)
        }
    }

    fun updateProfile(profile: ProfileModel) {
        viewModelScope.launch {
            repository.updateProfile(profile)
        }
    }
}