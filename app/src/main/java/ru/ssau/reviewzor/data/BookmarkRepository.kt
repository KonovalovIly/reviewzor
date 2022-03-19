package ru.ssau.reviewzor.data

import androidx.lifecycle.LiveData
import ru.ssau.reviewzor.data.db.PlaceBookDatabase
import ru.ssau.reviewzor.domain.entity.PlacesModel


class BookmarkRepository(private val db: PlaceBookDatabase) {

    suspend fun addBookmark(placesModel: PlacesModel): Long {
        val newId =  db.bookmarkDao().insertBookmark(placesModel)
        placesModel.id = newId
        return newId
    }

    fun createBookmark(): PlacesModel {
        return PlacesModel()
    }

    val allBookmarks: LiveData<List<PlacesModel>>
        get() {
            return db.bookmarkDao().loadAll()
        }
}