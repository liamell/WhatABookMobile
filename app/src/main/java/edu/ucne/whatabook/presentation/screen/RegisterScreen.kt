package edu.ucne.whatabook.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.whatabook.presentation.login.LoginViewModel
import androidx.compose.ui.tooling.preview.Preview
import edu.ucne.whatabook.ui.theme.WhatABookTheme

@Composable
fun RegisterScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        Spacer(Modifier.height(24.dp))


        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Usuario", color = Color.Black) },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )

        Spacer(Modifier.height(16.dp))


        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña", color = Color.Black) },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )

        Spacer(Modifier.height(28.dp))


        Button(
            onClick = {
                viewModel.register(user, pass)
                onBack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF702222),
                contentColor = Color.White
            )
        ) {
            Text("Registrar")
        }

        Spacer(Modifier.height(16.dp))


        TextButton(onClick = onBack) {
            Text("Volver al Login", color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    WhatABookTheme {
        RegisterScreen(
            onBack = {}
        )
    }
}
