package edu.ucne.whatabook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.ui.home.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearch: (String) -> Unit,
    onLibroClick: (Libro) -> Unit,
    onCarritoClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onFiltrarGenero: (Int) -> Unit // <<--- NUEVO
) {

    val generos = listOf(
        "Todos" to 0,
        "Romance" to 1,
        "Fantasía" to 2,
        "Misterio" to 3,
        "Terror" to 4
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedGenero by remember { mutableStateOf("Todos") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onHome = {},
                onCarrito = onCarritoClick,
                onPerfil = onPerfilClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFF7D0000)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WhatABook",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            var searchText by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearch(it)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                placeholder = { Text("Buscar...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(30.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))


            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(selectedGenero)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    generos.forEach { (nombre, id) ->
                        DropdownMenuItem(
                            text = { Text(nombre) },
                            onClick = {
                                selectedGenero = nombre
                                expanded = false
                                onFiltrarGenero(id)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(8.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(uiState.libros) { libro ->
                    LibroItem(
                        libro = libro,
                        onClick = { onLibroClick(libro) }
                    )
                }
            }
        }
    }
}


@Composable
fun LibroItem(libro: Libro, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .width(110.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = libro.imagenUrl,
            contentDescription = libro.titulo,
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = libro.titulo,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        Text(
            text = "${libro.precio} RD$",
            fontSize = 12.sp,
            color = Color.Blue
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Agregar")
        }
    }
}


@Composable
fun BottomNavigationBar(
    onHome: () -> Unit,
    onCarrito: () -> Unit,
    onPerfil: () -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF7D0000)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = onHome,
            icon = { Icon(Icons.Default.Home, contentDescription = null, tint = Color.White) },
            label = { Text("Inicio", color = Color.White) }
        )

        NavigationBarItem(
            selected = false,
            onClick = onCarrito,
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White) },
            label = { Text("Carrito", color = Color.White) }
        )

        NavigationBarItem(
            selected = false,
            onClick = onPerfil,
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.White) },
            label = { Text("Perfil", color = Color.White) }
        )
    }
}
