package com.example.demoarbitrosapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await // Importante para usar .await()
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess = _registrationSuccess.asStateFlow()

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "El correo y la contraseña no pueden estar vacíos."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _registrationSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = when {
                    e.message?.contains("The email address is badly formatted") == true ->
                        "El formato del correo no es válido."
                    e.message?.contains("The password must be 6 characters long or more") == true ||
                            e.message?.contains("Password should be at least 6 characters") == true ||
                            e.message?.contains("The given password is invalid") == true ->
                        "La contraseña debe tener al menos 6 caracteres."
                    e.message?.contains("The email address is already in use by another account") == true ->
                        "Este correo electrónico ya está registrado."
                    else -> "Error en el registro: ${e.message}"
                }
                _registrationSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
