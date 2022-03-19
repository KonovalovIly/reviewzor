package ru.ssau.reviewzor.presenter.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ssau.reviewzor.domain.entity.PlacesModel

class PlacesModelDiffCallback: DiffUtil.ItemCallback<PlacesModel>() {

    override fun areItemsTheSame(oldItem: PlacesModel, newItem: PlacesModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlacesModel, newItem: PlacesModel): Boolean {
        return oldItem == newItem
    }
}