package edu.ucne.whatabook.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // 🔑 Necesaria para Color.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.ucne.whatabook.R // Asegúrate de que R esté accesible

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.openbook),
            contentDescription = "Logo de WhatABook",
            modifier = Modifier.size(100.dp)
        )
    }
}