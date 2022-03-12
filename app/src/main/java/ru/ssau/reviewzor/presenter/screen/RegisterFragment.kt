package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.ssau.reviewzor.databinding.FragmentRegisterBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
    }

    private fun setNavigation() {
        binding.button.setOnClickListener {
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToMainContainerFragment()
            )
        }
    }
}