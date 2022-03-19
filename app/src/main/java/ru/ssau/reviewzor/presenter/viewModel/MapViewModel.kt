package ru.ssau.reviewzor.presenter.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.PointOfInterest
import ru.ssau.reviewzor.data.BookmarkRepository

class MapViewModel(private val repository: BookmarkRepository) : ViewModel() {

    suspend fun addBookmarkFromPlace(point: PointOfInterest) {
        val placesModel = repository.createBookmark()
        placesModel.name = point.name
        placesModel.longitude = point.latLng.longitude
        placesModel.latitude = point.latLng.latitude

        val newId = repository.addBookmark(placesModel)
        Log.i("MapViewModel", "New bookmark $newId added to the database.")
    }
}