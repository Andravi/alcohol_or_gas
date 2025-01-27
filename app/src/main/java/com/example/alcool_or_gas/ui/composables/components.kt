package com.example.alcool_or_gas.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcool_or_gas.priceValidator

@Composable
fun ResButton(onResActi: () -> Unit) {
    Button(
        onClick = { onResActi() },
        enabled = true,
        contentPadding = PaddingValues(42.dp, 12.dp),
        modifier = Modifier.padding(top = 40.dp)
    ) {
        Text(
            "CALCULAR",
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = 1.sp,
        )
    }
}

@Composable
fun Switch70Or75(checked: Boolean, onSwitched: (Boolean) -> Unit) {
    Text(
        "70%",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
    Switch(
        checked = checked,
        onCheckedChange = { che -> onSwitched(che) },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        }
    )
    Text(
        "75%",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}


@Composable
fun ResultadoText(bool: Boolean?) {
    if (bool == null) {
        return
    }
    if (bool) {
        Text(
            text = "Compensa",
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface
        )
    } else {
        Text(
            text = "Não compensa",
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}

@Composable
fun FuelInput(t: String, onChangeText: (String) -> Unit, tipo:String) {
    var text = t

    TextField(
        value = text, // The current text value
        onValueChange = {
            text = if (priceValidator(it)) {
                it
            } else {
                it.substring(0, it.length - 1)
            }
            onChangeText(text)
        },
        placeholder = {
            Text(
                "Digite o preço de ${tipo}, Ex: 5,40",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Thin
            )
        },
        leadingIcon = {
            Text(
                "R$ ",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary
        )
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.tertiary,
    ), title = {
        Text(
            "Fuel Insight", style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.tertiary
            )
        )
    })
}