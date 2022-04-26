package ru.ssau.reviewzor.data

import androidx.lifecycle.LiveData
import ru.ssau.reviewzor.data.db.PlaceBookDatabase
import ru.ssau.reviewzor.domain.entity.PlacesModel
import ru.ssau.reviewzor.domain.entity.ProfileModel


class BookmarkRepository(private val db: PlaceBookDatabase) {

    suspend fun addBookmark(placesModel: PlacesModel) {
        if (!db.bookmarkDao().contains(placesModel.id)) {
            db.bookmarkDao().insertBookmark(placesModel)
        }
    }

    val favoritesPlaces: LiveData<List<PlacesModel>>
        get() {
            return db.bookmarkDao().favoritesPlaces()
        }

    suspend fun getNotes(placeId: String) = db.bookmarkDao().loadBookmark(placeId)

    fun createBookmark(): PlacesModel {
        return PlacesModel()
    }

    suspend fun deleteBookmark(bookmark: PlacesModel) {
        db.bookmarkDao().deleteBookmark(bookmark)
    }

    suspend fun updateProfile(profileModel: ProfileModel) {
        db.bookmarkDao().insertProfile(profileModel)
    }

    suspend fun updateBookmark(place: PlacesModel) = db.bookmarkDao().updateBookmark(place)

    val allBookmarks: LiveData<List<PlacesModel>>
        get() {
            return db.bookmarkDao().loadAll()
        }

    val profile: LiveData<ProfileModel?>
        get() {
            return db.bookmarkDao().getProfile()
        }
}