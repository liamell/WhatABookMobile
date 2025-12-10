package edu.ucne.whatabook.presentation.screens.userscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.whatabook.R
import edu.ucne.whatabook.presentation.usuario.RegisterUiState
import edu.ucne.whatabook.presentation.usuario.RegisterViewModel
import edu.ucne.whatabook.ui.theme.WhatABookTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val appPrimaryRed = MaterialTheme.colorScheme.primary

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            onRegisterSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Image(
                        painter = painterResource(R.drawable.openbookwhite),
                        contentDescription = "Logo de WhatABook",
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = appPrimaryRed
                )
            )
        }
    ) { paddingValues ->
        RegisterContent(
            uiState = uiState,
            paddingValues = paddingValues,
            onNombreChange = viewModel::onNombreChange,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onRegisterClick = viewModel::register,
            onLoginClick = onLoginClick
        )
    }
}

@Composable
fun RegisterContent(
    uiState: RegisterUiState,
    paddingValues: PaddingValues,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

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
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "WhatABook",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = colors.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(R.drawable.openbook),
                        contentDescription = "Logo de WhatABook",
                        modifier = Modifier.size(30.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    "Crear una cuenta",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    color = colors.onSurfaceVariant
                )

                OutlinedTextField(
                    value = uiState.nombre,
                    onValueChange = onNombreChange,
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
                    onValueChange = onEmailChange,
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
                    onValueChange = onPasswordChange,
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
                    onClick = onRegisterClick,
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


// ======================================================
// PREVIEWS
// ======================================================

private class FakeRegisterViewModel(initialState: RegisterUiState) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) { /* No-op */ }
    fun onEmailChange(email: String) { /* No-op */ }
    fun onPasswordChange(password: String) { /* No-op */ }
    fun register() { /* No-op */ }
}

private class RegisterStateProvider : PreviewParameterProvider<RegisterUiState> {
    override val values = sequenceOf(
        RegisterUiState( // Estado Normal
            nombre = "Jane Doe",
            email = "jane@example.com",
            password = "SecurePassword123"
        ),
        RegisterUiState( // Estado con Errores
            nombre = "J",
            email = "bademail",
            password = "123",
            nombreError = "El nombre es muy corto.",
            emailError = "Formato de email incorrecto.",
            passwordError = "La contraseña debe tener 6 caracteres.",
            error = "Verifica los campos obligatorios."
        ),
        RegisterUiState( // Estado de Carga
            loading = true
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Register Screen")
@Composable
private fun RegisterScreenPreview(@PreviewParameter(RegisterStateProvider::class) uiState: RegisterUiState) {
    WhatABookTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(R.drawable.openbookwhite),
                            contentDescription = "Logo de WhatABook",
                            modifier = Modifier.size(32.dp),
                            contentScale = ContentScale.Fit
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { paddingValues ->
            // Usamos un Fake ViewModel para pasar el estado de muestra
            val fakeViewModel = FakeRegisterViewModel(uiState)
            RegisterContent(
                uiState = fakeViewModel.uiState.collectAsState().value,
                paddingValues = paddingValues,
                onNombreChange = {},
                onEmailChange = {},
                onPasswordChange = {},
                onRegisterClick = {},
                onLoginClick = {}
            )
        }
    }
}