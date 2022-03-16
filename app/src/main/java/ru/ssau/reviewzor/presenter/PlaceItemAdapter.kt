package ru.ssau.reviewzor.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ssau.reviewzor.R
import ru.ssau.reviewzor.presenter.model.PlaceItem

class PlaceItemAdapter(
    var places: List<PlaceItem>
) : RecyclerView.Adapter<PlaceItemAdapter.PlaceItemViewHolder>() {

    inner class PlaceItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return PlaceItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.itemView.apply {
            //findViewById<ImageView>(R.id.image_view)
            findViewById<TextView>(R.id.place_title).text = places[position].placeTitle
            findViewById<TextView>(R.id.place_rating).text = places[position].placeRating
            findViewById<CheckBox>(R.id.checkBox).isChecked = places[position].isChecked
        }
    }

    override fun getItemCount(): Int {
        return places.size
    }
}