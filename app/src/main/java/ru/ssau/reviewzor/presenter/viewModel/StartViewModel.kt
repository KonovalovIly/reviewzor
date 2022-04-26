package ru.ssau.reviewzor.presenter.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ssau.reviewzor.data.TokenRepository

class StartViewModel(private val repository: TokenRepository) : ViewModel() {

    val nextScreen = MutableLiveData(false)

    init {
        getToken()
    }

    private fun getToken() {
        val token = repository.getToken()
        Log.d("tag", token.toString())
        if (token?.isNotEmpty() == true)nextScreen.value = true
    }
}