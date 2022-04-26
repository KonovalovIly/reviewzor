package ru.ssau.reviewzor.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileModel(
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val secondName: String = "",
    val image: String = "",
)
