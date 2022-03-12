package ru.ssau.reviewzor.presenter.screen

import android.view.LayoutInflater
import ru.ssau.reviewzor.databinding.FragmentListPlacesBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class ListPlacesFragment : BaseFragment<FragmentListPlacesBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentListPlacesBinding =
        FragmentListPlacesBinding.inflate(inflater)


}