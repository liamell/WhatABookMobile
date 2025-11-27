package edu.ucne.whatabook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.whatabook.ui.home.HomeViewModel
import edu.ucne.whatabook.ui.screens.HomeScreen
import edu.ucne.whatabook.ui.theme.WhatABookTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val homeViewModel: HomeViewModel = viewModel()

            WhatABookTheme {

                HomeScreen(
                    uiState = homeViewModel.uiState,
                    onSearch = homeViewModel::search,
                    onLibroClick = {},
                    onCarritoClick = {},
                    onPerfilClick = {},
                    onFiltrarGenero = homeViewModel::filtrarPorGenero
                )
            }
        }
    }
}
