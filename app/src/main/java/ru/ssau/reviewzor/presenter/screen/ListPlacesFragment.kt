package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ssau.reviewzor.databinding.FragmentListPlacesBinding
import ru.ssau.reviewzor.presenter.PlaceItemAdapter
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.model.PlaceItem

class ListPlacesFragment : BaseFragment<FragmentListPlacesBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentListPlacesBinding =
        FragmentListPlacesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val places = mutableListOf(
            PlaceItem(null,"Title_1", "5", false),
            PlaceItem(null, "Title_2", "4,7", false),
            PlaceItem(null, "Title_3", "2", true)
        )
        val adapter = PlaceItemAdapter(places)
        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = LinearLayoutManager(this.context)

    }

}