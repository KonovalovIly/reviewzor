package ru.ssau.reviewzor.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ssau.reviewzor.data.TokenRepository
import ru.ssau.reviewzor.presenter.viewModel.*

val appModule = module {

    viewModel<MapViewModel> {
        MapViewModel(repository = get())
    }

    viewModel {
        ListViewModel(repository = get())
    }

    viewModel {
        DetailViewModel(repository = get(), networkRepository = get(), tokenRepository = get())
    }

    viewModel {
        ProfileViewModel(repository = get(), tokenRepository = get())
    }

    viewModel {
        RegisterViewModel(repository = get())
    }

    viewModel {
        AuthViewModel(repository = get(), tokenRepository = get())
    }

    viewModel {
        StartViewModel(repository = get())
    }
}
