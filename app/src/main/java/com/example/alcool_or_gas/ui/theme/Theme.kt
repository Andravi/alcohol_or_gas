package com.example.alcool_or_gas.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    secondary = DarkBlue,
    tertiary = Orange,
    background = White,
    onBackground = LightGray,
    surface = DarkGray,
    onSurface = DarkBlack
)

private val LightColorScheme = lightColorScheme(
    primary = DarkGreen,
    secondary = LightBlue,
    tertiary = Orange,
    background = DarkBlack,
    onBackground = DarkGray,
    surface = LightGray,
    onSurface = White
)

@Composable
fun Alcool_or_gasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Deixa as cores do usuário sobrescrever as suas
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> LightColorScheme
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, // Usando o colorScheme gerado
        typography = Typography,    // Defina sua tipografia aqui
        content = content
    )
}