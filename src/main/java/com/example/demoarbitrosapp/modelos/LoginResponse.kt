package com.example.demoarbitrosapp.modelos

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para recibir la respuesta del servidor tras un login exitoso.
 * @SerializedName se usa para mapear un nombre del JSON a un nombre de variable diferente en Kotlin.
 */
data class LoginResponse(
    // Mapea el campo "token" del JSON a la variable "authToken".
    @SerializedName("token")
    val authToken: String,

    // Puedes añadir cualquier otro campo que te devuelva la API.
    val message: String?
)
