package edu.ucne.whatabook.presentation.screens.userscreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import edu.ucne.whatabook.navigation.BottomNavigationBar
import edu.ucne.whatabook.presentation.usuario.ProfileUiState
import edu.ucne.whatabook.presentation.usuario.ProfileViewModel
import edu.ucne.whatabook.ui.theme.WhatABookTheme
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToHistory: () -> Unit = {},
    onNavigate: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val appPrimaryRed = MaterialTheme.colorScheme.primary

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.toString()?.let {
            viewModel.updateProfileImage(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text("Mi Perfil", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = appPrimaryRed
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onHome = { onNavigate("home") },
                onCarrito = { onNavigate("carrito") },
                onPerfil = { },
                currentRoute = 3
            )
        }
    ) { paddingValues ->
        ProfileContent(
            state = state,
            paddingValues = paddingValues,
            onProfileImageClick = { imagePicker.launch("image/*") },
            onNavigateToHistory = onNavigateToHistory,
            onLogoutClick = {
                viewModel.logout()
                onNavigate("login")
            }
        )
    }
}

@Composable
fun ProfileContent(
    state: ProfileUiState,
    paddingValues: PaddingValues,
    onProfileImageClick: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val appPrimaryRed = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(100.dp)
                .clickable(onClick = onProfileImageClick),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (!state.profileImageUri.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(state.profileImageUri),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileTextField(
                    label = "Nombre",
                    value = state.nombre,
                    onValueChange = {},
                    icon = Icons.Filled.Person,
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextField(
                    label = "Email",
                    value = state.email,
                    onValueChange = {},
                    icon = Icons.Filled.MailOutline,
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextField(
                    label = "Contraseña",
                    value = "••••••••",
                    onValueChange = {},
                    icon = Icons.Filled.Lock,
                    isPassword = true,
                    readOnly = true
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onNavigateToHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Historial de Compras", color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = appPrimaryRed)
        ) {
            Text("Cerrar Sesión")
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    readOnly: Boolean = false,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        readOnly = readOnly,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
}

private val SampleProfileState = ProfileUiState(
    nombre = "María Rodríguez",
    email = "maria.rodriguez@ucne.edu.do",
    profileImageUri = "https://picsum.photos/id/1005/200/200"
)

private val SampleProfileStateNoAvatar = ProfileUiState(
    nombre = "Juan Pérez",
    email = "juan.perez@ucne.edu.do",
    profileImageUri = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "1. Perfil - Con Imagen")
@Composable
private fun ProfileContentWithImagePreview() {
    WhatABookTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mi Perfil") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            bottomBar = {
                BottomNavigationBar(onHome = { }, onCarrito = { }, onPerfil = { }, currentRoute = 3)
            }
        ) { paddingValues ->
            ProfileContent(
                state = SampleProfileState,
                paddingValues = paddingValues,
                onProfileImageClick = {},
                onNavigateToHistory = {},
                onLogoutClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "2. Perfil - Sin Imagen (Avatar)")
@Composable
private fun ProfileContentNoImagePreview() {
    WhatABookTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mi Perfil") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            bottomBar = {
                BottomNavigationBar(onHome = { }, onCarrito = { }, onPerfil = { }, currentRoute = 3)
            }
        ) { paddingValues ->
            ProfileContent(
                state = SampleProfileStateNoAvatar,
                paddingValues = paddingValues,
                onProfileImageClick = {},
                onNavigateToHistory = {},
                onLogoutClick = {}
            )
        }
    }
}