package ru.ssau.reviewzor.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import ru.ssau.reviewzor.domain.entity.PlacesModel

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM PlacesModel")
    fun loadAll(): LiveData<List<PlacesModel>>

    @Query("SELECT * FROM PlacesModel WHERE id = :bookmarkId")
    suspend fun loadBookmark(bookmarkId: Long): PlacesModel

    @Query("SELECT * FROM PlacesModel WHERE id = :bookmarkId")
    fun loadLiveBookmark(bookmarkId: Long): LiveData<PlacesModel>

    @Insert(onConflict = IGNORE)
    suspend fun insertBookmark(bookmark: PlacesModel): Long

    @Update(onConflict = REPLACE)
    suspend fun updateBookmark(bookmark: PlacesModel)

    @Delete
    suspend fun deleteBookmark(bookmark: PlacesModel)
}