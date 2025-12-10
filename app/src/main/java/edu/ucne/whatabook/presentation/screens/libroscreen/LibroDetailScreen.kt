package edu.ucne.whatabook.presentation.screens.libroscreen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.ui.theme.WhatABookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroDetailScreen(
    libro: Libro?,
    onAgregarCarrito: () -> Unit,
    onVolver: () -> Unit
) {
    if (libro == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando o libro no encontrado...")
        }
        return
    }

    val isDark = isSystemInDarkTheme()
    val themeColors = MaterialTheme.colorScheme

    val appPrimaryRed = themeColors.primary
    val blueAction = themeColors.secondary
    val cardBackground = if (isDark) themeColors.surfaceVariant else themeColors.surface
    val textPrimary = themeColors.onSurface
    val textSecondary = themeColors.onSurfaceVariant

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(libro.titulo, maxLines = 1, color = themeColors.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = themeColors.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = appPrimaryRed)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    AsyncImage(
                        model = libro.imagenUrl,
                        contentDescription = libro.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(libro.titulo, fontSize = 24.sp, color = textPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Autor: ${libro.autores}", fontSize = 16.sp, color = textSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Descripción:", fontSize = 16.sp, color = textPrimary)
                    Text(libro.descripcion, fontSize = 14.sp, color = textSecondary)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (libro.precio > 0.0) {
                        Text("Precio: ${String.format("%,.2f RD$", libro.precio)}", fontSize = 16.sp, color = blueAction)
                    } else {
                        Text("Precio: No disponible", fontSize = 16.sp, color = textSecondary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = onAgregarCarrito,
                            modifier = Modifier
                                .width(72.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = blueAction)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Agregar al carrito",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



private val SampleBookNormal = Libro(
    libroId = 1,
    titulo = "El Principito",
    autores = "Antoine de Saint-Exupéry",
    descripcion = "Una historia filosófica y poética sobre la amistad, el amor, la pérdida y el sentido de la vida.",
    precio = 15.99,
    imagenUrl = "https://ejemplo.com/principito.jpg",
    generoId = 1,
    cantidad = 5
)

private val SampleBookCeroPrecio = SampleBookNormal.copy(
    titulo = "El Arte de la Guerra (Promoción)",
    precio = 0.0,
    descripcion = "Un clásico militar sobre estrategia, distribuido como promoción gratuita."
)

@Preview(showBackground = true, name = "1. Detalle - Precio Normal (Light)")
@Composable
private fun LibroDetailNormalPreview() {
    WhatABookTheme {
        LibroDetailScreen(
            libro = SampleBookNormal,
            onAgregarCarrito = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true, name = "2. Detalle - Precio Cero (Light)")
@Composable
private fun LibroDetailCeroPrecioPreview() {
    WhatABookTheme {
        LibroDetailScreen(
            libro = SampleBookCeroPrecio,
            onAgregarCarrito = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true, name = "3. Detalle - Cargando/Null (Light)")
@Composable
private fun LibroDetailNullPreview() {
    WhatABookTheme {
        LibroDetailScreen(
            libro = null,
            onAgregarCarrito = {},
            onVolver = {}
        )
    }
}