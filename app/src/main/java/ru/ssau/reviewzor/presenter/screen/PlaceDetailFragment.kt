package ru.ssau.reviewzor.presenter.screen

import android.view.LayoutInflater
import ru.ssau.reviewzor.databinding.FragmentPlaceDetailBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class PlaceDetailFragment : BaseFragment<FragmentPlaceDetailBinding>() {
    override fun initBinding(inflater: LayoutInflater): FragmentPlaceDetailBinding =
        FragmentPlaceDetailBinding.inflate(inflater)
}