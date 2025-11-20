package com.example.demoarbitrosapp.modelos

/**
 * Modelo de datos para enviar las credenciales en la petición de login.
 * Los nombres de las variables ('email', 'contrasena') deben coincidir exactamente
 * con los que espera recibir tu API en el JSON.
 */
data class LoginRequest(
    val email: String,
    val contrasena: String
)
