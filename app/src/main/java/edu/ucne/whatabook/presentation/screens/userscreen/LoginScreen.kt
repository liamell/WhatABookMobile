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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import edu.ucne.whatabook.presentation.usuario.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme

    if (uiState.success) {
        onLoginSuccess()
    }

    Box(Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.libraly),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
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
                    "Log in to your account",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    color = colors.onSurfaceVariant
                )

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.onSurfaceVariant,
                        unfocusedTextColor = colors.onSurfaceVariant,
                        focusedBorderColor = colors.primary,
                        unfocusedBorderColor = colors.onSurfaceVariant,
                        cursorColor = colors.primary,
                        focusedLabelColor = colors.primary,
                        unfocusedLabelColor = colors.onSurfaceVariant
                    )
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.onSurfaceVariant,
                        unfocusedTextColor = colors.onSurfaceVariant,
                        focusedBorderColor = colors.primary,
                        unfocusedBorderColor = colors.onSurfaceVariant,
                        cursorColor = colors.primary,
                        focusedLabelColor = colors.primary,
                        unfocusedLabelColor = colors.onSurfaceVariant
                    )
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = viewModel::login,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Log in", color = colors.onPrimary)
                }

                uiState.error?.let {
                    Spacer(Modifier.height(10.dp))
                    Text(it, color = Color.Red)
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = onRegisterClick) {
                    Text(
                        "Register as a new user",
                        color = colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}