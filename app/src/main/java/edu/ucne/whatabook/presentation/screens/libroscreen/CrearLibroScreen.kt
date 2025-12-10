package edu.ucne.whatabook.presentation.screens.libroscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.presentation.libros.LibroEvent
import edu.ucne.whatabook.presentation.libros.LibroViewModel
import edu.ucne.whatabook.ui.theme.WhatABookTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearLibroScreen(
    viewModel: LibroViewModel,
    onVolver: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    CrearLibroForm(
        titulo = uiState.titulo,
        autor = uiState.autor,
        descripcion = uiState.descripcion,
        precio = uiState.precio,
        cantidad = uiState.cantidad,
        imagenUrl = uiState.imagenUrl,
        generoId = uiState.generoId ?: 0,
        onTituloChange = viewModel::onTituloChange,
        onAutorChange = viewModel::onAutorChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onPrecioChange = viewModel::onPrecioChange,
        onCantidadChange = viewModel::onCantidadChange,
        onImagenUrlChange = viewModel::onImagenUrlChange,
        onGeneroIdChange = viewModel::onGeneroIdChange,
        onGuardarClick = {
            val generoIdSeleccionado = uiState.generoId

            if (generoIdSeleccionado != null) {
                val nuevoLibro = Libro(
                    libroId = 0,
                    titulo = uiState.titulo.trim(),
                    autores = uiState.autor.trim(),
                    descripcion = uiState.descripcion.trim(),
                    precio = uiState.precio.toDoubleOrNull() ?: 0.01,
                    imagenUrl = uiState.imagenUrl,
                    cantidad = uiState.cantidad.toIntOrNull() ?: 1,
                    generoId = generoIdSeleccionado
                )

                coroutineScope.launch {
                    viewModel.onEvent(LibroEvent.CrearLibro(nuevoLibro))
                    viewModel.clearFormFields()
                    onVolver()
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearLibroForm(
    titulo: String,
    autor: String,
    descripcion: String,
    precio: String,
    cantidad: String,
    imagenUrl: String,
    generoId: Int,
    onTituloChange: (String) -> Unit,
    onAutorChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    onCantidadChange: (String) -> Unit,
    onImagenUrlChange: (String) -> Unit,
    onGeneroIdChange: (Int) -> Unit,
    onGuardarClick: () -> Unit
) {
    val generos = listOf(
        1 to "Romance", 2 to "Fantasía", 3 to "Misterio", 4 to "Terror",
        5 to "Acción", 6 to "Aventura", 7 to "Literatura Juvenil", 8 to "Ciencia Ficción"
    )

    var expanded by remember { mutableStateOf(false) }
    val generoSeleccionado = generos.find { it.first == generoId }

    val fieldModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Crear Nuevo Libro",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = onTituloChange,
            label = { Text("Título") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = autor,
            onValueChange = onAutorChange,
            label = { Text("Autor") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = onDescripcionChange,
            label = { Text("Descripción") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = onPrecioChange,
            label = { Text("Precio") },
            modifier = fieldModifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = onCantidadChange,
            label = { Text("Cantidad (Stock Inicial)") },
            modifier = fieldModifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = imagenUrl,
            onValueChange = onImagenUrlChange,
            label = { Text("URL de Imagen") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                readOnly = true,
                value = generoSeleccionado?.second ?: "Seleccionar Género",
                onValueChange = {},
                label = { Text("Género") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = textFieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                generos.forEach { (id, nombre) ->
                    DropdownMenuItem(
                        text = { Text(nombre, color = MaterialTheme.colorScheme.onSurface) },
                        onClick = {
                            onGeneroIdChange(id)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGuardarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "Guardar Libro",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@Preview(showBackground = true, name = "Crear Libro - Formulario Vacio")
@Composable
private fun CrearLibroFormPreview() {
    WhatABookTheme {
        CrearLibroForm(
            titulo = "",
            autor = "",
            descripcion = "",
            precio = "",
            cantidad = "",
            imagenUrl = "",
            generoId = 0, // No seleccionado
            onTituloChange = {},
            onAutorChange = {},
            onDescripcionChange = {},
            onPrecioChange = {},
            onCantidadChange = {},
            onImagenUrlChange = {},
            onGeneroIdChange = {},
            onGuardarClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Crear Libro - Formulario Lleno")
@Composable
private fun CrearLibroFormLlenoPreview() {
    WhatABookTheme {
        CrearLibroForm(
            titulo = "Cien Años de Soledad",
            autor = "Gabriel García Márquez",
            descripcion = "Una novela de realismo mágico.",
            precio = "19.99",
            cantidad = "50",
            imagenUrl = "http://ejemplo.com/imagen.jpg",
            generoId = 2, // Fantasía
            onTituloChange = {},
            onAutorChange = {},
            onDescripcionChange = {},
            onPrecioChange = {},
            onCantidadChange = {},
            onImagenUrlChange = {},
            onGeneroIdChange = {},
            onGuardarClick = {}
        )
    }
}