package ru.ssau.reviewzor.presenter.screen

import android.view.LayoutInflater
import ru.ssau.reviewzor.databinding.FragmentProfileBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater)

}