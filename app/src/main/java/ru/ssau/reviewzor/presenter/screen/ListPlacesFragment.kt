package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import ru.ssau.reviewzor.databinding.FragmentListPlacesBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class ListPlacesFragment : BaseFragment<FragmentListPlacesBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentListPlacesBinding =
        FragmentListPlacesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TODO("Задать ресайкл адаптер")
    }

}