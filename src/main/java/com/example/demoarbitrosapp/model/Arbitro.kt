package com.example.demoarbitrosapp.model

/**
 * Representa un árbitro.
 * El 'id' es un Int y es opcional (nullable), ya que al crear un nuevo árbitro
 * desde la app, no tenemos un ID hasta que el servidor lo asigna.
 */
data class Arbitro(
    val id: Int? = null,
    val nombre: String,
    val categoria: String
)
