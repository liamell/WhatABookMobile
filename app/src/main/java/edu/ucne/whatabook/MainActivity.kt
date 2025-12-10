package edu.ucne.whatabook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.whatabook.navigation.AppNavHost // Importación clave
import edu.ucne.whatabook.ui.theme.WhatABookTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WhatABookTheme {

                val navController = rememberNavController()


                AppNavHost(navController = navController)
            }
        }
    }
}