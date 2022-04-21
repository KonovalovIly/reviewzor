package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.databinding.FragmentProfileBinding
import ru.ssau.reviewzor.domain.entity.ProfileModel
import ru.ssau.reviewzor.presenter.adapter.PlaceItemAdapter
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.ProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val profileViewModel by viewModel<ProfileViewModel>()

    private val adapter: PlaceItemAdapter by lazy { PlaceItemAdapter(requireContext()) }

    override fun initBinding(inflater: LayoutInflater): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
    }

    private fun setObserver() {
        profileViewModel.favoritesPlaces.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        profileViewModel.profile?.observe(viewLifecycleOwner) {
            binding.editTextName.setText(it?.name)
            binding.editTextSecondName.setText(it?.secondName)
        }
    }

    private fun setAdapter() {
        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = LinearLayoutManager(this.context)
        adapter.onFollow = {
            profileViewModel.update(it)
        }
        adapter.onClick = {
            findNavController().navigate(
                ListPlacesFragmentDirections.actionListPlacesFragmentToPlaceDetailFragment(it)
            )
        }

        binding.edit.setOnClickListener {
            profileViewModel.updateProfile(
                ProfileModel(
                    name = binding.editTextName.text.toString(),
                    secondName = binding.editTextSecondName.text.toString()
                )
            )
        }
    }


}