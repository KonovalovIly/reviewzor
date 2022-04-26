package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.databinding.FragmentLoginBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.AuthViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authViewModel by viewModel<AuthViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        setObserver()
    }

    private fun setNavigation() {
        binding.button.setOnClickListener {
            authViewModel.auth(
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
        }
    }

    private fun setObserver() {
        authViewModel.nextScreen.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToMainContainerFragment()
            )
        }
        authViewModel.error.observe(viewLifecycleOwner) {
            Log.d("ErrorRegister", it.message.toString())
        }
    }
}