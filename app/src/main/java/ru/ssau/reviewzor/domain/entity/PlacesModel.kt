package ru.ssau.reviewzor.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlacesModel(
    @PrimaryKey var id: String = "",
    var name: String = "",
    val category: String = "",
    val follow: Boolean = false,
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    val detail: String = "",
    val rating: Double = 0.0,
    val image: String = "",
)
