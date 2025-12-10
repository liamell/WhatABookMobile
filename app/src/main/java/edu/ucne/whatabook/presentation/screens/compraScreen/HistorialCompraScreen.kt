package edu.ucne.whatabook.presentation.screens.compraScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.ucne.whatabook.data.local.entity.CompraConDetalles
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.presentation.compra.CompraViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialComprasScreen(
    viewModel: CompraViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    val historial by viewModel.historial.collectAsState()
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) { viewModel.cargarHistorial() }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Historial",
                            tint = colors.onPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Historial de Compras",
                            color = colors.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = colors.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary
                )
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(historial) { compraConDetalles ->
                    CompraCard(compraConDetalles, viewModel)
                }
            }
        }
    )
}

@Composable
fun CompraCard(compraConDetalles: CompraConDetalles, viewModel: CompraViewModel) {
    val compra = compraConDetalles.compra
    val detalles = compraConDetalles.detalles
    val fechaFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val fechaStr = fechaFormat.format(Date(compra.fecha))
    val colors = MaterialTheme.colorScheme

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fechaStr,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = colors.onSurface
                )
                Text(
                    text = "Total: ${String.format("%,.2f RD$", compra.total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = colors.onSurface
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            detalles.forEach { detalle ->
                var libro by remember { mutableStateOf<Libro?>(null) }

                LaunchedEffect(detalle.libroId) {
                    libro = viewModel.getLibroByIdCached(detalle.libroId)
                }

                libro?.let { l ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = l.imagenUrl,
                            contentDescription = l.titulo,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = l.titulo,
                                fontWeight = FontWeight.Medium,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = colors.onSurface
                            )
                            Text(
                                text = "Cantidad: ${detalle.cantidad} | Precio: ${String.format("%,.2f RD$", detalle.precio)}",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                color = colors.onSurfaceVariant
                            )
                        }
                    }
                    Divider(
                        color = colors.onSurface.copy(alpha = 0.3f),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}
