package com.example.alcool_or_gas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.alcool_or_gas.R

// Carregando a fonte Roboto
val Roboto = FontFamily(
    Font(R.font.roboto_regular),  // Regular
    Font(R.font.roboto_bold, FontWeight.Bold)  // Bold
)

val Typography = Typography(
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.25.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.25.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.25.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,  // Fonte Roboto
        fontWeight = FontWeight.Normal,  // Peso normal
        fontSize = 16.sp,      // Tamanho de fonte
        lineHeight = 24.sp,    // Altura da linha
        letterSpacing = 0.5.sp // Espaçamento entre letras
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp
    ),
)