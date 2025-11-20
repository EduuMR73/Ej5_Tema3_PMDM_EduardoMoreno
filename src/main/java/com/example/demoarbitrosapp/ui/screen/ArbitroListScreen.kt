package com.example.demoarbitrosapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoarbitrosapp.model.Arbitro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArbitroListScreen(viewModel: ArbitrosViewModel = hiltViewModel()) {
    val arbitros by viewModel.arbitros.collectAsState()
    val context = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var arbitroEditando by rememberSaveable { mutableStateOf<Arbitro?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Árbitros") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir árbitro")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (arbitros.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay árbitros para mostrar.")
                }
            } else {
                LazyColumn {
                    items(arbitros, key = { it.id ?: it.hashCode() }) { arbitro ->
                        ArbitroItem(
                            arbitro = arbitro,
                            onEdit = { arbitroEditando = it },
                            onDelete = { viewModel.deleteArbitro(it.id!!) }
                        )
                        Divider()
                    }
                }
            }

            if (showDialog) {
                ArbitroDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { nombre, categoria ->
                        viewModel.addArbitro(nombre, categoria)
                        showDialog = false
                        Toast.makeText(context, "Árbitro añadido", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            if (arbitroEditando != null) {
                ArbitroDialog(
                    nombreInicial = arbitroEditando!!.nombre,
                    categoriaInicial = arbitroEditando!!.categoria,
                    esEdicion = true,
                    onDismiss = { arbitroEditando = null },
                    onConfirm = { nombre, categoria ->
                        val arbitroActualizado = arbitroEditando!!.copy(
                            nombre = nombre,
                            categoria = categoria
                        )
                        viewModel.updateArbitro(arbitroActualizado)
                        arbitroEditando = null
                        Toast.makeText(context, "Árbitro actualizado", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun ArbitroItem(
    arbitro: Arbitro,
    onEdit: (Arbitro) -> Unit,
    onDelete: (Arbitro) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // ESTE CAMBIO es CLAVE para centrar
    ) {
        Text(
            text = arbitro.nombre,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = arbitro.categoria,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onEdit(arbitro) }) {
            Icon(Icons.Default.Edit, contentDescription = "Editar")
        }
        IconButton(onClick = { onDelete(arbitro) }) {
            Icon(Icons.Default.Delete, contentDescription = "Borrar")
        }
    }
}
