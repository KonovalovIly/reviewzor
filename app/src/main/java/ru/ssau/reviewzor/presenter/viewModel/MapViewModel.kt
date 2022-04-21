package ru.ssau.reviewzor.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.ssau.reviewzor.data.BookmarkRepository

class MapViewModel(private val repository: BookmarkRepository) : ViewModel() {

    suspend fun addBookmarkFromPlace(id: String, name: String, latLng: LatLng, address: String) {
        val placesModel = repository.createBookmark()
        placesModel.id = id
        placesModel.address = address
        placesModel.name = name
        placesModel.longitude = latLng.longitude
        placesModel.latitude = latLng.latitude
        repository.addBookmark(placesModel)
    }

    fun getImage(id: String): String? = runBlocking {
        return@runBlocking repository.getNotes(id)?.image
    }
}