package ru.ssau.reviewzor.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import ru.ssau.reviewzor.domain.entity.PlacesModel
import ru.ssau.reviewzor.domain.entity.ProfileModel

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM PlacesModel")
    fun loadAll(): LiveData<List<PlacesModel>>

    @Query("SELECT * FROM PlacesModel WHERE id = :placeId")
    suspend fun loadBookmark(placeId: String): PlacesModel?

    @Query("SELECT * FROM PlacesModel WHERE id = :placeId")
    fun loadLiveBookmark(placeId: String): LiveData<PlacesModel>

    @Insert(onConflict = IGNORE)
    suspend fun insertBookmark(bookmark: PlacesModel): Long

    @Update(onConflict = REPLACE)
    suspend fun updateBookmark(bookmark: PlacesModel)

    @Delete
    suspend fun deleteBookmark(bookmark: PlacesModel)

    @Query("SELECT EXISTS (SELECT * FROM PlacesModel WHERE id = :placeId)")
    suspend fun contains(placeId: String): Boolean

    @Query("SELECT * FROM PlacesModel WHERE follow = 1")
    fun favoritesPlaces(): LiveData<List<PlacesModel>>

    @Query("SELECT * FROM ProfileModel")
    fun getProfile(): LiveData<ProfileModel?>

    @Insert(onConflict = REPLACE)
    suspend fun insertProfile(profile: ProfileModel)

}