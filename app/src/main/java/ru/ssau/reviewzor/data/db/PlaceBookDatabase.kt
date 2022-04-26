package ru.ssau.reviewzor.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.ssau.reviewzor.domain.entity.PlacesModel
import ru.ssau.reviewzor.domain.entity.ProfileModel

@Database(
    entities = [PlacesModel::class, ProfileModel::class], version = 6,
)
abstract class PlaceBookDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {

        private var instance: PlaceBookDatabase? = null
        fun getInstance(context: Context): PlaceBookDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceBookDatabase::class.java,
                    "PlaceBook"
                ).fallbackToDestructiveMigration().build()
            }

            return instance as PlaceBookDatabase
        }
    }
}