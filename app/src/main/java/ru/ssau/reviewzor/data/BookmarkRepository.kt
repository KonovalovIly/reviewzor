package ru.ssau.reviewzor.data

import androidx.lifecycle.LiveData
import ru.ssau.reviewzor.data.db.PlaceBookDatabase
import ru.ssau.reviewzor.domain.entity.PlacesModel


class BookmarkRepository(private val db: PlaceBookDatabase) {

    suspend fun addBookmark(placesModel: PlacesModel) {
        if (!db.bookmarkDao().contains(placesModel.id)) {
            db.bookmarkDao().insertBookmark(placesModel)
        }
    }

    suspend fun getNotes(placeId: String) = db.bookmarkDao().loadBookmark(placeId)

    fun createBookmark(): PlacesModel {
        return PlacesModel()
    }

    suspend fun updateBookmark(place: PlacesModel) = db.bookmarkDao().updateBookmark(place)

    val allBookmarks: LiveData<List<PlacesModel>>
        get() {
            return db.bookmarkDao().loadAll()
        }
}