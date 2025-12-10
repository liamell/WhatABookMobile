package edu.ucne.whatabook.presentation.screens.libroscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.presentation.home.HomeUiState
import edu.ucne.whatabook.navigation.BottomNavigationBar
import edu.ucne.whatabook.ui.theme.CardDark
import edu.ucne.whatabook.ui.theme.CardWhite
import edu.ucne.whatabook.ui.theme.WhatABookTheme
import edu.ucne.whatabook.R //

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearch: (String) -> Unit,
    onDetalleLibro: (Libro) -> Unit,
    onAgregarCarrito: (Libro) -> Unit,
    onCarritoClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onFiltrarGenero: (Int) -> Unit,
    onCrearLibroClick: () -> Unit
) {
    val generos = listOf(
        "Todos" to 0, "Romance" to 1, "Fantasía" to 2, "Misterio" to 3, "Terror" to 4,
        "Accion" to 5, "Aventura" to 6, "Literatura Juvenil" to 7, "Ciencia Ficcion" to 8
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedGenero by remember { mutableStateOf("Todos") }
    var searchText by remember { mutableStateOf("") }

    val searchFontSize = 16.sp
    val isFilterActive = searchText.isNotEmpty() || selectedGenero != "Todos"

    val idToNameMap = remember { generos.associate { (name, id) -> id to name } }

    val librosAgrupados = remember(uiState.libros, isFilterActive) {
        if (isFilterActive) emptyMap()
        else getLibrosAgrupados(uiState.libros, idToNameMap)
    }

    LaunchedEffect(Unit) { onFiltrarGenero(0) }

    val backgroundColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface
    val borderColor = MaterialTheme.colorScheme.outline
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(65.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Grupo: Título y Icono
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // CAMBIO: Añadimos un Spacer para "bajar" el título
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                "WhatABook",
                                color = Color(0xFF7D0000),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.width(8.dp))


                            Icon(
                                painter = painterResource(id = R.drawable.openbook),
                                contentDescription = "Libro",
                                tint = Color(0xFF7D0000),
                                modifier = Modifier.size(28.dp)
                            )
                        }


                        Spacer(modifier = Modifier.width(0.dp))
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onHome = {},
                onCarrito = onCarritoClick,
                onPerfil = onPerfilClick,
                currentRoute = 1
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearch(it)
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                placeholder = { Text("Buscar...", fontSize = searchFontSize) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = searchFontSize, color = textColor),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    cursorColor = borderColor,
                    focusedPlaceholderColor = placeholderColor,
                    unfocusedPlaceholderColor = placeholderColor,
                    focusedLeadingIconColor = borderColor,
                    unfocusedLeadingIconColor = borderColor,
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = backgroundColor)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filtrar por género", tint = textColor)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Filtrar por: $selectedGenero",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
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
                            if (nombre != "Todos") searchText = ""
                            onFiltrarGenero(id)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFF7D0000))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Sincronizando libros...", color = textColor)
                    }
                }
            } else if (isFilterActive || librosAgrupados.isEmpty()) {
                val listToDisplay = if (isFilterActive) uiState.libros else emptyList()

                if (listToDisplay.isEmpty() && !uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "No se encontraron libros para el filtro o búsqueda.",
                            color = textColor,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(listToDisplay.chunked(2)) { rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowItems.forEach { libro ->
                                    LibroItem(
                                        libro = libro,
                                        onDetalle = { onDetalleLibro(libro) },
                                        onAgregarCarrito = { onAgregarCarrito(libro) },
                                        textColor = textColor,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (rowItems.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            } else if (librosAgrupados.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    librosAgrupados.forEach { (genero, libros) ->
                        item {
                            Text(
                                text = genero,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = textColor,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                            )
                        }

                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp)
                            ) {
                                items(libros) { libro ->
                                    LibroItem(
                                        libro = libro,
                                        onDetalle = { onDetalleLibro(libro) },
                                        onAgregarCarrito = { onAgregarCarrito(libro) },
                                        textColor = textColor
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(20.dp)) }
                    }
                }
            }
        }
    }
}

fun getLibrosAgrupados(
    libros: List<Libro>,
    generoMap: Map<Int, String>
): Map<String, List<Libro>> {
    return libros
        .groupBy { libro ->
            generoMap[libro.generoId] ?: "Otros"
        }
        .toSortedMap()
}

@Composable
fun LibroItem(
    libro: Libro,
    onDetalle: () -> Unit,
    onAgregarCarrito: () -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val blueAction = MaterialTheme.colorScheme.secondary
    val precioFormateado = "%,d".format(libro.precio.toInt())

    val realCardColor = if (isSystemInDarkTheme()) CardDark else CardWhite

    Card(
        modifier = modifier
            .padding(6.dp)
            .width(135.dp)
            .height(270.dp)
            .shadow(12.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = realCardColor),
        border = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clickable { onDetalle() }
            ) {
                AsyncImage(
                    model = libro.imagenUrl,
                    contentDescription = libro.titulo,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onDetalle() }
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    libro.titulo,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                Text(
                    "$precioFormateado RD$",
                    fontSize = 12.sp,
                    color = blueAction,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = onAgregarCarrito,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Agregar", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}