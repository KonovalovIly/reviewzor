package ru.ssau.reviewzor.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.ssau.reviewzor.databinding.FragmentMainContainerBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment

class MainContainerFragment : BaseFragment<FragmentMainContainerBinding>() {

    override fun initBinding(inflater: LayoutInflater): FragmentMainContainerBinding =
        FragmentMainContainerBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
    }

    private fun setNavigation() {
        val bottomNavigation = binding.bottomNavigation
        val navigationController =
            ((childFragmentManager.findFragmentById(binding.mainContainer.id)) as NavHostFragment)
                .navController
        bottomNavigation.setupWithNavController(navigationController)
    }
}