package com.example.alcool_or_gas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcool_or_gas.ui.theme.Alcool_or_gasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Alcool_or_gasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun calcular() {
    Log.d("testes", "Funciona")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(name: String, modifier: Modifier = Modifier) {
    Scaffold(topBar = { topbar() }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(15.dp))
                    .align(Alignment.CenterHorizontally)
                    .size(500.dp, 800.dp)
                    .padding(0.dp,16.dp)
            ) {
                Spacer(modifier = Modifier.size(0.dp,48.dp))
                Image(
                    painter = painterResource(id = R.drawable.fuel_insight_logo_removebg),
                    contentDescription = "some thing",
                    modifier = Modifier.size(
                        200.dp
                    )
                )
                Text(
                    text = "Qual o Combustível mais vantajoso ?",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface

                )
                Text(text = "Preencha os valores e Calcule!", fontStyle = FontStyle.Italic, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.size(0.dp,32.dp))
                Text(
                    text = "Preço da Gasolina:", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Start).padding(48.dp,4.dp),
                )
                gasInput()
                Spacer(modifier = Modifier.size(0.dp,16.dp))
                Text(
                    text = "Preço da Álcool:", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Start).padding(48.dp,8.dp),
                )
                gasInput()
                Spacer(modifier = Modifier.size(0.dp,24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.size(width = 130.dp, 32.dp)
                ) {
                    Text("70%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Switch70Or75()
                    Text("75%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }

                CalculateButton { calcular() }


            }
        }
    }

}

@Composable
fun CalculateButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
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
fun Switch70Or75() {
    var checked by remember { mutableStateOf(false) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
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

}

@Composable
fun gasInput() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text, // The current text value
        onValueChange = { newText -> text = newText },
        placeholder = {
            Text("Digite o preço do litro, Ex: 5,40", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Thin)
        },
        leadingIcon = {
            Text("R$ ", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
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

//    OutlinedTextField(
//        value = text, // The current text value
//        onValueChange = { newText -> text = newText },
//        placeholder = {
//            Text("Digite o preço do litro, Ex: 5,40")
//        },
//        prefix = {
//            Text("R$ ", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
//        },
//        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//        colors = OutlinedTextFieldDefaults.colors(
//            cursorColor = MaterialTheme.colorScheme.secondary,
//            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
//            focusedLabelColor = MaterialTheme.colorScheme.secondary,
//            focusedTextColor = MaterialTheme.colorScheme.secondary,
//            focusedBorderColor = MaterialTheme.colorScheme.secondary,
//        ),
//    )


}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Alcool_or_gasTheme {
        App("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topbar() {
    TopAppBar(colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.tertiary,
    ), title = {
        Text("Fuel Insight", style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.tertiary
        ))
    })
}