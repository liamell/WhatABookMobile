package edu.ucne.whatabook.presentation.screens.cartscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import edu.ucne.whatabook.presentation.carrito.CartUiState
import edu.ucne.whatabook.presentation.carrito.isFormularioValido

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    uiState: CartUiState,
    onValueChange: (String, String, String, String) -> Unit,
    onConfirmarCompra: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val rojoOscuro = Color(0xFF7D0000)
    val azulConfirmar = Color(0xFF1E88E5)

    val isTarjetaValida = uiState.numeroTarjeta.replace(" ", "").length == 16
    val isNombreValido = uiState.nombreTitular.length >= 4
    val isFechaValida = uiState.fechaExp.matches(Regex("""\d{2}/\d{2}"""))
    val isCvvValido = uiState.cvv.length == 3

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.CreditCard,
                            contentDescription = "Ícono de tarjeta",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Checkout y Pago", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = rojoOscuro),
                navigationIcon = {
                    IconButton(onClick = { onNavigate("carrito") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver al carrito", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {

            Text(
                "Información de la Tarjeta",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )


            OutlinedTextField(
                value = uiState.numeroTarjeta,
                onValueChange = {
                    val text = it.filter { char -> char.isDigit() }
                    val formatted = text.chunked(4).joinToString(" ")
                    onValueChange(formatted, uiState.nombreTitular, uiState.fechaExp, uiState.cvv)
                },
                label = { Text("Número de tarjeta") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.numeroTarjeta.isNotEmpty() && !isTarjetaValida,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = azulConfirmar,
                    unfocusedBorderColor = if (uiState.numeroTarjeta.isNotEmpty() && !isTarjetaValida) Color.Red else Color.Gray,
                    errorBorderColor = Color.Red,
                    cursorColor = azulConfirmar,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                )
            )
            if (uiState.numeroTarjeta.isNotEmpty() && !isTarjetaValida) {
                Text("Número de tarjeta debe tener 16 dígitos", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))


            OutlinedTextField(
                value = uiState.nombreTitular,
                onValueChange = { onValueChange(uiState.numeroTarjeta, it, uiState.fechaExp, uiState.cvv) },
                label = { Text("Nombre del titular") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.nombreTitular.isNotEmpty() && !isNombreValido,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = azulConfirmar,
                    unfocusedBorderColor = if (uiState.nombreTitular.isNotEmpty() && !isNombreValido) Color.Red else Color.Gray,
                    errorBorderColor = Color.Red,
                    cursorColor = azulConfirmar,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                )
            )
            if (uiState.nombreTitular.isNotEmpty() && !isNombreValido) {
                Text("Nombre debe tener al menos 4 caracteres", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                OutlinedTextField(
                    value = uiState.fechaExp,
                    onValueChange = {
                        val text = it.filter { char -> char.isDigit() }
                        val formatted = when {
                            text.length >= 3 -> text.substring(0,2) + "/" + text.substring(2, minOf(4,text.length))
                            else -> text
                        }
                        onValueChange(uiState.numeroTarjeta, uiState.nombreTitular, formatted, uiState.cvv)
                    },
                    label = { Text("Exp (MM/YY)") },
                    modifier = Modifier.weight(1f).padding(end = 6.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.fechaExp.isNotEmpty() && !isFechaValida,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = azulConfirmar,
                        unfocusedBorderColor = if (uiState.fechaExp.isNotEmpty() && !isFechaValida) Color.Red else Color.Gray,
                        errorBorderColor = Color.Red,
                        cursorColor = azulConfirmar,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = uiState.cvv,
                    onValueChange = { onValueChange(uiState.numeroTarjeta, uiState.nombreTitular, uiState.fechaExp, it.take(3)) },
                    label = { Text("CVV") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f).padding(start = 6.dp),
                    isError = uiState.cvv.isNotEmpty() && !isCvvValido,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = azulConfirmar,
                        unfocusedBorderColor = if (uiState.cvv.isNotEmpty() && !isCvvValido) Color.Red else Color.Gray,
                        errorBorderColor = Color.Red,
                        cursorColor = azulConfirmar,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                if (uiState.fechaExp.isNotEmpty() && !isFechaValida) {
                    Text("Formato MM/YY", color = Color.Red, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f).padding(end = 6.dp))
                } else {
                    Spacer(modifier = Modifier.weight(1f).padding(end = 6.dp))
                }

                if (uiState.cvv.isNotEmpty() && !isCvvValido) {
                    Text("CVV 3 dígitos", color = Color.Red, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f).padding(start = 6.dp))
                } else {
                    Spacer(modifier = Modifier.weight(1f).padding(start = 6.dp))
                }
            }

            Spacer(Modifier.height(30.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total a Pagar:", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "${uiState.total} RD$",
                        style = MaterialTheme.typography.titleLarge.copy(color = rojoOscuro, fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (isFormularioValido(uiState)) {
                        onConfirmarCompra()
                        onNavigate("home")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = isFormularioValido(uiState),
                colors = ButtonDefaults.buttonColors(
                    containerColor = azulConfirmar,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Confirmar Compra", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
