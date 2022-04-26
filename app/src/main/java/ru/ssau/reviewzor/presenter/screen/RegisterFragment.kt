package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.databinding.FragmentRegisterBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.RegisterViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val registerViewModel by viewModel<RegisterViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        setObserver()
    }

    private fun setNavigation() {
        binding.button.setOnClickListener {
            registerViewModel.register(
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
        }
    }

    private fun setObserver() {
        registerViewModel.nextScreen.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
        }
        registerViewModel.error.observe(viewLifecycleOwner) {
            Log.d("ErrorRegister", it.message.toString())
        }
    }
}