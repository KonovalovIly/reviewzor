package ru.ssau.reviewzor.presenter.screen

import android.view.LayoutInflater
import ru.ssau.reviewzor.databinding.FragmentStartScreenBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment


class StartScreen : BaseFragment<FragmentStartScreenBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentStartScreenBinding =
        FragmentStartScreenBinding.inflate(layoutInflater)


}