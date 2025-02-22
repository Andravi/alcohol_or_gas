package com.example.alcool_or_gas.views

import com.example.alcool_or_gas.R


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.alcool_or_gas.ui.composables.FuelInput
import com.example.alcool_or_gas.ui.composables.ResButton
import com.example.alcool_or_gas.ui.composables.ResultadoText
import com.example.alcool_or_gas.ui.composables.Switch70Or75
import com.example.alcool_or_gas.ui.composables.TopBar
import com.example.alcool_or_gas.ui.theme.Alcool_or_gasTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlcoolOrGas(navController: NavHostController, check: Boolean) {
    val context = LocalContext.current
    var alcoolText by rememberSaveable { mutableStateOf("") }
    var gasText by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(check) }
    var res: Boolean? by rememberSaveable { mutableStateOf(null) }

    Scaffold(topBar = { TopBar() }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("ListaDePostos/add/")
            },
        ) {
            Icon(Icons.Filled.Add, "Inserir Posto")
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .align(Alignment.CenterHorizontally)
                    .size(500.dp, 800.dp)
                    .padding(0.dp, 16.dp)
            ) {
                Spacer(Modifier.padding(top = 100.dp))
                Text(
                    text = "Qual o Combustível mais vantajoso ?",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface

                )

                Text(
                    text = "Preencha os valores e Calcule!",
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.size(0.dp, 32.dp))

                Text(
                    text = "Preço da Gasolina:", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(48.dp, 4.dp),
                )

                Spacer(modifier = Modifier.size(0.dp, 16.dp))

                FuelInput(gasText, onChangeText = { gasText = it }, "Gasolina")

                Text(
                    text = "Preço da Álcool:", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(48.dp, 8.dp),
                )

                Spacer(modifier = Modifier.size(0.dp, 24.dp))

                FuelInput(alcoolText, onChangeText = { alcoolText = it }, "Alcool")

                Spacer(modifier = Modifier.size(0.dp, 16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.size(width = 130.dp, 32.dp)
                ) {
                    Switch70Or75(checked, onSwitched = {
                        checked = it;
                        saveConfig(context, checked)
                    })
                }
                ResultadoText(res)
                ResButton {
                    res = isAlcoolBetter(
                        alcoolText.let {
                            if (it.isNotEmpty()) {
                                it.replace(',', '.').toDouble()
                            } else {
                                0.0
                            }
                        },
                        gasText.let {
                            if (it.isNotEmpty()) {
                                it.replace(',', '.').toDouble()
                            } else {
                                0.0
                            }
                        },
                        if (checked) 0.75f else 0.70f
                    )
                    Log.d("teste", "asd")
                }
            }
        }
    }
}


fun priceValidator(text: String): Boolean {
    val pattern = Regex("^\\d+(,\\d*)?\$")
    return (text.isEmpty() || text.matches(pattern)) && text.count { it == ',' } <= 1
}

fun isAlcoolBetter(a: Double, g: Double, factor: Float): Boolean {
    val rent = a / g
    println(rent)
    if (rent <= factor) {
        return true
    }
    return false
}

fun saveConfig(context: Context, switch_state: Boolean) {
    val sharedFileName = "config_Alc_ou_Gas"
    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    var editor = sp.edit()
    editor.putBoolean("is_75_checked", switch_state)
    editor.apply()
}


//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//    Alcool_or_gasTheme {
//        AlcoolOrGas()
//    }
//}
