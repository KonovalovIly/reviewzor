package ru.ssau.reviewzor.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.PointOfInterest
import ru.ssau.reviewzor.data.BookmarkRepository

class MapViewModel(private val repository: BookmarkRepository) : ViewModel() {

    val bookmarks = repository.allBookmarks

    suspend fun addBookmarkFromPlace(point: PointOfInterest) {
        val placesModel = repository.createBookmark()
        placesModel.id = point.placeId
        placesModel.name = point.name
        placesModel.longitude = point.latLng.longitude
        placesModel.latitude = point.latLng.latitude
        repository.addBookmark(placesModel)
    }
}