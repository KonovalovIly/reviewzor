package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.databinding.FragmentStartScreenBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.StartViewModel


class StartScreen : BaseFragment<FragmentStartScreenBinding>() {

    private val startViewModel by viewModel<StartViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentStartScreenBinding =
        FragmentStartScreenBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        setObserver()
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

    private fun setObserver() {
        startViewModel.nextScreen.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    StartScreenDirections.actionStartScreenToMainContainerFragment()
                )
            }
        }
    }
}