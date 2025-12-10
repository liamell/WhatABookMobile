package edu.ucne.whatabook.presentation.screens.userscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.whatabook.R
import edu.ucne.whatabook.presentation.usuario.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            onRegisterSuccess()
        }
    }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colors.onSurfaceVariant,
        unfocusedTextColor = colors.onSurfaceVariant,
        focusedBorderColor = colors.primary,
        unfocusedBorderColor = colors.onSurfaceVariant,
        cursorColor = colors.primary,
        focusedLabelColor = colors.primary,
        unfocusedLabelColor = colors.onSurfaceVariant
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.libraly),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.30f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .statusBarsPadding()
                .graphicsLayer { alpha = 0.92f }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colors.surfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "WhatABook",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = colors.primary
                )

                Text(
                    "Crear una cuenta",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    color = colors.onSurfaceVariant
                )

                OutlinedTextField(
                    value = uiState.nombre,
                    onValueChange = viewModel::onNombreChange,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.nombreError != null,
                    shape = RoundedCornerShape(12.dp),
                    colors = textFieldColors
                )
                uiState.nombreError?.let {
                    Text(it, color = Color.Red, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.emailError != null,
                    shape = RoundedCornerShape(12.dp),
                    colors = textFieldColors
                )
                uiState.emailError?.let {
                    Text(it, color = Color.Red, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.passwordError != null,
                    shape = RoundedCornerShape(12.dp),
                    colors = textFieldColors
                )
                uiState.passwordError?.let {
                    Text(it, color = Color.Red, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = viewModel::register,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.loading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary
                    )
                ) {
                    Text("Registrarse", color = colors.onPrimary)
                }

                uiState.error?.let {
                    Spacer(Modifier.height(10.dp))
                    Text(it, color = Color.Red)
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = onLoginClick) {
                    Text(
                        "¿Ya tienes cuenta? Inicia sesión",
                        color = colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}