package edu.ucne.whatabook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.whatabook.presentation.login.LoginViewModel
import edu.ucne.whatabook.presentation.screen.LoginScreen
import edu.ucne.whatabook.presentation.screen.RegisterScreen
import edu.ucne.whatabook.ui.theme.WhatABookTheme
import edu.ucne.whatabook.ui.theme.WhatABookTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatABookTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: LoginViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {

                    },
                    onOpenRegister = { navController.navigate("register") }
                )
            }

            composable("register") {
                RegisterScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
