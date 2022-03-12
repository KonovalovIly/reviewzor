package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.ssau.reviewzor.databinding.FragmentStartScreenBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment


class StartScreen : BaseFragment<FragmentStartScreenBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentStartScreenBinding =
        FragmentStartScreenBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
    }

    private fun setNavigation() {
        binding.logIn.setOnClickListener {
            findNavController().navigate(
                StartScreenDirections.actionStartScreenToLoginFragment()
            )
        }

        binding.register.setOnClickListener {
            findNavController().navigate(
                StartScreenDirections.actionStartScreenToRegisterFragment()
            )
        }
    }
}