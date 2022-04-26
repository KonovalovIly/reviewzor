package ru.ssau.reviewzor.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.reviewzor.data.NetworkRepository

class RegisterViewModel(private val repository: NetworkRepository) : ViewModel() {

    val nextScreen = MutableLiveData<Boolean>(false)

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun register(username: String, password: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching { repository.registerUser(username, password) }
            result.onSuccess {
                Log.d("ErrorRegister", it.message)
                when (it.message) {
                    "Error: Username is exists" -> nextScreen.value = true
                    "User CREATED" -> nextScreen.value = true
                }
            }
            result.onFailure {
                _error.value = it
            }
        }
    }
}