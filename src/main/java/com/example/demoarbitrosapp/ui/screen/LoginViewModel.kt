package com.example.demoarbitrosapp.ui.screen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun login(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = task.exception?.localizedMessage ?: "Error desconocido"
                }
            }
    }

    fun register(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = task.exception?.localizedMessage ?: "Error desconocido"
                }
            }
    }

    fun checkIfUserIsLoggedIn() {
        val user = auth.currentUser
        _loginSuccess.value = user != null
    }

    fun logout() {
        auth.signOut()
        _loginSuccess.value = false
        _errorMessage.value = null
        _isLoading.value = false
    }
}
