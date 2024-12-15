package com.example.alcool_or_gas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alcool_or_gas.ui.theme.Alcool_or_gasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Alcool_or_gasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(name: String, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { topbar() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .align(Alignment.CenterHorizontally)
                    .size(300.dp, 600.dp)
                    .padding(15.dp)

            ) {
                Text(text = "icon")
                Text(text = "Alcool ou Gasolina?")
                Text(text = "Preço da gasolina : [Input decimal]")
                Text(text = "Preço da Alcool : [Input decimal]")
                Text(text = "Seletor 70% ou 75%")
                Text(text = "Resultado")
                Text(text = "Botão de calcular")


            }
        }
    }

}

@Composable
fun TitleText() {
    return
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
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.tertiary,
        ),
        title = {
            Text("Com qual combustível?")
        }
    )
}