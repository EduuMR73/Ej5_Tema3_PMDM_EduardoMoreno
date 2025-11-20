package com.example.demoarbitrosapp.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoarbitrosapp.data.ArbitroApiService
import com.example.demoarbitrosapp.model.Arbitro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ArbitrosViewModel_API"

@HiltViewModel
class ArbitrosViewModel @Inject constructor(
    private val apiService: ArbitroApiService
) : ViewModel() {

    private val _arbitros = MutableStateFlow<List<Arbitro>>(emptyList())
    val arbitros = _arbitros.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        Log.d(TAG, "ViewModel inicializado. Cargando árbitros desde la API de Render...")
        fetchArbitros()
    }

    private fun fetchArbitros() {
        viewModelScope.launch {
            try {
                val arbitrosList = apiService.getArbitros()
                Log.d(TAG, "Datos recibidos de la API. Total: ${arbitrosList.size}")
                _arbitros.value = arbitrosList
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener datos de la API: ", e)
                _errorMessage.value = "No se pudieron cargar los datos: ${e.message}"
            }
        }
    }

    fun addArbitro(nombre: String, categoria: String) {
        if (nombre.isBlank() || categoria.isBlank()) {
            _errorMessage.value = "El nombre y la categoría no pueden estar vacíos."
            return
        }
        viewModelScope.launch {
            try {
                val nuevoArbitro = Arbitro(nombre = nombre, categoria = categoria)
                val response = apiService.addArbitro(nuevoArbitro)
                if (response.isSuccessful) {
                    fetchArbitros()
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Error al guardar el árbitro: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "No se pudo conectar con el servidor: ${e.message}"
            }
        }
    }

    fun updateArbitro(arbitro: Arbitro) {
        viewModelScope.launch {
            try {
                val response = apiService.updateArbitro(arbitro.id!!, arbitro)
                if (response.isSuccessful) {
                    fetchArbitros()
                } else {
                    _errorMessage.value = "Error al actualizar: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "No se pudo actualizar: ${e.message}"
            }
        }
    }

    fun deleteArbitro(arbitroId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteArbitro(arbitroId)
                if (response.isSuccessful) {
                    fetchArbitros()
                } else {
                    _errorMessage.value = "Error al borrar: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "No se pudo borrar: ${e.message}"
            }
        }
    }
}
