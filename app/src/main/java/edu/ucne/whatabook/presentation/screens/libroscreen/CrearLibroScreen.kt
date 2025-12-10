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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.presentation.libros.LibroEvent
import edu.ucne.whatabook.presentation.libros.LibroViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearLibroScreen(
    viewModel: LibroViewModel,
    onVolver: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val generos = listOf(
        1 to "Romance",
        2 to "Fantasía",
        3 to "Misterio",
        4 to "Terror",
        5 to "Acción",
        6 to "Aventura",
        7 to "Literatura Juvenil",
        8 to "Ciencia Ficción"
    )

    var expanded by remember { mutableStateOf(false) }
    val generoSeleccionado = generos.find { it.first == uiState.generoId }

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
            value = uiState.titulo,
            onValueChange = viewModel::onTituloChange,
            label = { Text("Título") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.autor,
            onValueChange = viewModel::onAutorChange,
            label = { Text("Autor") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.descripcion,
            onValueChange = viewModel::onDescripcionChange,
            label = { Text("Descripción") },
            modifier = fieldModifier,
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.precio,
            onValueChange = viewModel::onPrecioChange,
            label = { Text("Precio") },
            modifier = fieldModifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.cantidad,
            onValueChange = viewModel::onCantidadChange,
            label = { Text("Cantidad (Stock Inicial)") },
            modifier = fieldModifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.imagenUrl,
            onValueChange = viewModel::onImagenUrlChange,
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
                            viewModel.onGeneroIdChange(id)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
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
            },
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