package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.databinding.FragmentPlaceDetailBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.DetailViewModel

class PlaceDetailFragment : BaseFragment<FragmentPlaceDetailBinding>() {

    private val args by navArgs<PlaceDetailFragmentArgs>()
    private val detailViewModel by viewModel<DetailViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentPlaceDetailBinding =
        FragmentPlaceDetailBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.sendResponse(args.name)
        initBinding()
        editListener()
    }

    private fun initBinding() {
        detailViewModel.bookmark.observe(viewLifecycleOwner) {
            binding.apply {
                editTextName.setText(it.name)
                editTextAddress.setText(it.address)
                editTextTextDetail.setText(it.detail)
                editRating.setText(it.rating.toString())
            }
        }
    }

    private fun editListener() {
        binding.edit.setOnClickListener {
            val bookplace = detailViewModel.bookmark.value
            if (bookplace != null) {
                detailViewModel.update(
                    name = binding.editTextName.text.toString(),
                    address = binding.editTextAddress.text.toString(),
                    detail = binding.editTextTextDetail.text.toString(),
                    category = "",
                    rating = binding.editRating.text.toString().toDouble(),
                )
            }
        }
    }
}