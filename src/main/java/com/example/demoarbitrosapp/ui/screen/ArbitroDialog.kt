package com.example.demoarbitrosapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun ArbitroDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, categoria: String) -> Unit,
    nombreInicial: String = "",
    categoriaInicial: String = "",
    esEdicion: Boolean = false
) {
    var nombre by rememberSaveable { mutableStateOf(nombreInicial) }
    var categoria by rememberSaveable { mutableStateOf(categoriaInicial) }
    val isConfirmEnabled by remember {
        derivedStateOf { nombre.isNotBlank() && categoria.isNotBlank() }
    }

    val categoriaFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (esEdicion) "Editar Árbitro" else "Añadir Árbitro") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del árbitro") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { categoriaFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(categoriaFocusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (isConfirmEnabled) {
                                keyboardController?.hide()
                                onConfirm(nombre, categoria)
                            }
                        }
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(nombre, categoria) },
                enabled = isConfirmEnabled
            ) {
                Text(if (esEdicion) "Guardar" else "Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
