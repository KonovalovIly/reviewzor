package ru.ssau.reviewzor.presenter.model

import android.media.Image

class PlaceItem(
    var image: Image?,
    var placeTitle: String,
    var placeRating: String,
    var isChecked: Boolean
)