package ru.ssau.reviewzor.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ssau.reviewzor.presenter.viewModel.DetailViewModel
import ru.ssau.reviewzor.presenter.viewModel.ListViewModel
import ru.ssau.reviewzor.presenter.viewModel.MapViewModel

val appModule = module {

    viewModel<MapViewModel> {
        MapViewModel(repository = get())
    }

    viewModel {
        ListViewModel(repository = get())
    }

    viewModel {
        DetailViewModel(repository = get())
    }
}
