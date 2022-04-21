package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.databinding.FragmentListPlacesBinding
import ru.ssau.reviewzor.presenter.adapter.PlaceItemAdapter
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.viewModel.ListViewModel

class ListPlacesFragment : BaseFragment<FragmentListPlacesBinding>() {

    private val listViewModel by viewModel<ListViewModel>()

    private val adapter: PlaceItemAdapter by lazy{ PlaceItemAdapter(requireContext()) }

    override fun initBinding(inflater: LayoutInflater): FragmentListPlacesBinding =
        FragmentListPlacesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
    }

    private fun setObserver() {
        listViewModel.listPlaces.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setAdapter() {
        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = LinearLayoutManager(this.context)
        adapter.onFollow = {
            listViewModel.update(it)
        }
        adapter.onClick = {
            findNavController().navigate(
                ListPlacesFragmentDirections.actionListPlacesFragmentToPlaceDetailFragment(it)
            )
        }
    }

}