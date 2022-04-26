package ru.ssau.reviewzor.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.BookmarkRepository
import ru.ssau.reviewzor.data.NetworkRepository
import ru.ssau.reviewzor.data.TokenRepository
import ru.ssau.reviewzor.domain.entity.PlacesModel
import ru.ssau.reviewzor.domain.entity.ProfileModel
import java.io.File

class ProfileViewModel(
    private val repository: BookmarkRepository,
    private val tokenRepository: TokenRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {

    val exit = MutableLiveData<Boolean>(false)

    val imageUrl = MutableLiveData<String>("")

    var favoritesPlaces = repository.favoritesPlaces

    var profile = repository.profile

    fun update(bookmark: PlacesModel) {
        viewModelScope.launch {
            repository.updateBookmark(bookmark)
        }
    }

    fun updateProfile(name: String, secondName: String) {
        viewModelScope.launch {
            repository.updateProfile(
                ProfileModel(
                    name = name,
                    secondName = secondName,
                    image = imageUrl.value.toString()
                )
            )
        }
    }

    fun deleteToken() {
        tokenRepository.deleteToken()
        exit.value = true
    }

    fun uploadPhoto(file: File) {
        viewModelScope.launch {
            kotlin.runCatching {
                networkRepository.uploadPhoto(file, tokenRepository.getToken())
            }.onSuccess {
                Log.d("Logger", it)
                imageUrl.value = "https://reviewzor-api.herokuapp.com/images/$it"
            }.onFailure {
                Log.d("Logger", it.message.toString())
            }
        }
    }
}