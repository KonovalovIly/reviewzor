package ru.ssau.reviewzor.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.NetworkRepository
import ru.ssau.reviewzor.data.TokenRepository

class AuthViewModel(
    private val repository: NetworkRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    val nextScreen = MutableLiveData<Boolean>(false)

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun auth(username: String, password: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching { repository.signIn(username, password) }
            result.onSuccess {
                Log.d("ErrorRegister", it.token)
                tokenRepository.saveToken(it.token)
                nextScreen.value = true
            }
            result.onFailure {
                _error.value = it
            }
        }
    }
}