package ru.ssau.reviewzor.presenter.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import coil.load
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import ru.ssau.reviewzor.databinding.ContentBookmarkInfoBinding

class BookmarkInfoWindowAdapter(context: Activity) : GoogleMap.InfoWindowAdapter {

    private val binding = ContentBookmarkInfoBinding.inflate(context.layoutInflater)

    override fun getInfoContents(marker: Marker): View {
        binding.placeTitle.text = marker.title ?: ""
        binding.placeRating.text = marker.snippet ?: ""
        val imageView = binding.imageView
        if (marker.tag is Int) {
            imageView.setImageResource((marker.tag as Int))
        } else {
            imageView.load((marker.tag as String))
        }
        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? = null


}