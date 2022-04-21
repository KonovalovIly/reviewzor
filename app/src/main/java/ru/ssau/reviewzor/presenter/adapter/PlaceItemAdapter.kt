package ru.ssau.reviewzor.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ssau.reviewzor.databinding.PlaceItemBinding
import ru.ssau.reviewzor.domain.entity.PlacesModel

class PlaceItemAdapter :
    ListAdapter<PlacesModel, PlaceItemAdapter.PlaceItemViewHolder>(PlacesModelDiffCallback()) {

    var onClick: ((placeName: String) -> Unit)? = null
    var onFollow: ((placeName: PlacesModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceItemViewHolder =
        PlaceItemViewHolder(
            PlaceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlaceItemViewHolder(binding: PlaceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val tvPlaceTitle = binding.placeTitle
        private val tvPlaceRating = binding.placeRating
        private val cbFollow = binding.checkBox
        private val card = binding.cardView

        fun bind(item: PlacesModel) {
            tvPlaceRating.text = item.rating.toString()
            tvPlaceTitle.text = item.name
            cbFollow.isChecked = item.follow
            card.setOnClickListener{
                onClick?.invoke(item.id)
            }
            cbFollow.setOnClickListener {
                val model = PlacesModel(
                    id = item.id,
                    name = item.name,
                    category = item.category,
                    follow = cbFollow.isChecked,
                    address = item.address,
                    latitude = item.latitude,
                    longitude = item.longitude,
                    detail = item.detail,
                    rating = item.rating,
                    image = item.image
                )
                onFollow?.invoke(model)
            }
        }
    }
}