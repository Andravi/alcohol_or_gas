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
    primary = DarkGreen,       // Verde Escuro como cor principal
    secondary = DarkBlue,     // Azul Escuro como cor secundária
    tertiary = Orange,        // Laranja como cor terciária
    background = DarkBlack,   // Preto como fundo
    onBackground = DarkWhite, // Branco para o texto sobre o fundo preto
    surface = DarkBlack,      // Fundo de superfície também em preto
    onSurface = White         // Branco para o texto sobre a superfície preta
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,      // Verde Claro como cor principal
    secondary = LightBlue,    // Azul Claro como cor secundária
    tertiary = Yellow,        // Amarelo como cor terciária
    background = White,       // Branco como fundo
    onBackground = DarkGray,  // Texto em cinza escuro sobre fundo branco
    surface = LightGray,      // Superfície em cinza claro
    onSurface = DarkGray      // Texto em cinza escuro sobre a superfície
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

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, // Usando o colorScheme gerado
        typography = Typography,    // Defina sua tipografia aqui
        content = content
    )
}