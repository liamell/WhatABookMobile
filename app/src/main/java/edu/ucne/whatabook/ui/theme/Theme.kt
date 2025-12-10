package edu.ucne.whatabook.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


val AppRed = Color(0xFF7D0000)
val AppBlue = Color(0xFF007BFF)


val AppRedLight = Color(0xFFFFD6D6)
val AppRedLightOn = Color(0xFF440000)


val AppBlueLight = Color(0xFFD6E8FF)
val AppBlueLightOn = Color(0xFF002A66)


val CardWhite = Color(0xFFFFFFFF)

val CardDark = Color(0xFF1A1A1A)


private val LightColorScheme = lightColorScheme(
    primary = AppRed,
    onPrimary = Color.White,

    primaryContainer = AppRedLight,
    onPrimaryContainer = AppRedLightOn,

    secondary = AppBlue,
    onSecondary = Color.White,

    secondaryContainer = AppBlueLight,
    onSecondaryContainer = AppBlueLightOn,

    error = Color(0xFFD32F2F),
    onError = Color.White,

    background = Color.White,
    onBackground = Color(0xFF222222),

    surface = Color.White,
    onSurface = Color(0xFF333333),

    // 👇 MUY IMPORTANTE
    // Aquí hacemos que los cards USEN el blanco puro
    surfaceVariant = CardWhite,
    onSurfaceVariant = Color(0xFF333333)
)


private val DarkColorScheme = darkColorScheme(
    primary = AppRed,
    onPrimary = Color.White,

    primaryContainer = AppRedLightOn,
    onPrimaryContainer = Color.White,

    secondary = AppBlue,
    onSecondary = Color.Black,

    secondaryContainer = AppBlueLightOn,
    onSecondaryContainer = Color.White,

    error = Color(0xFFFF7B7B),
    onError = Color.Black,

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF121212),
    onSurface = Color.White,

    surfaceVariant = CardDark,
    onSurfaceVariant = Color(0xFFEFEFEF)
)

@Composable
fun WhatABookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
