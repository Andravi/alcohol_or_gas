package com.example.alcool_or_gas.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.alcool_or_gas.R
import com.example.alcool_or_gas.ui.composables.FuelInput
import com.example.alcool_or_gas.ui.composables.ResButton
import com.example.alcool_or_gas.ui.composables.ResultadoText
import com.example.alcool_or_gas.ui.composables.Switch70Or75
import com.example.alcool_or_gas.ui.composables.TopBar
import com.example.alcool_or_gas.ui.theme.Alcool_or_gasTheme
import com.google.android.gms.location.LocationServices


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlcoolOrGas(navController: NavHostController?, check: Boolean) {
    val context = LocalContext.current
    var gasStationName by rememberSaveable { mutableStateOf("") }
    var alcoolText by rememberSaveable { mutableStateOf("") }
    var gasText by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(check) }
    var res: Boolean? by rememberSaveable { mutableStateOf(null) }

    // Por causa do tempo a parte de escolha será para quando tiver mais tempo
    // Melhorar para que caso não tenha a localização usar a padrão

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            Log.d("DEBUG", "${it.key} = ${it.value}")
        }
    }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var location = remember { mutableStateOf<Location?>(null) }
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions
            requestPermissionsLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return@LaunchedEffect
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { locatio : Location? ->
                // Got last known location. In some rare situations this can be null.
                location.value = locatio
                Log.d("teste", locatio.toString())
                Log.d("teste", location.value.toString())
            }
        // Atualmente está com um erro que demora até pegar a localização atual
    }



    Scaffold(topBar = { TopBar() }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                Log.d("teste", location.value!!.latitude.toString())
                val lat = location.value!!.latitude.toString()
                val long = location.value!!.longitude.toString()
                navController?.navigate("ListaDePostos/add/$gasStationName/$alcoolText/$gasText/$lat/$long")
            },
        ) {
            Icon(Icons.Filled.Add, stringResource(R.string.inserir_posto))
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

                Row(
                    Modifier
                        .padding(top = 45.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.veja_os_postos_j_cadastrados),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    FloatingActionButton(
                        onClick = {
                            navController?.navigate("listaDePostos//////")
                        },
                    ) {
                        Icon(Icons.AutoMirrored.Filled.List, stringResource(R.string.ver_postos))
                    }
                }
                Text(
                    text = stringResource(R.string.qual_o_combust_vel_mais_vantajoso),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface

                )

                Text(
                    text = stringResource(R.string.preencha_os_valores_e_calcule),
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.size(0.dp, 32.dp))

                Text(
                    text = stringResource(R.string.nome_do_posto_opcional), fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(48.dp, 8.dp),
                )

                TextField(
                    value = gasStationName, // The current text value
                    onValueChange = {
                        gasStationName = it

                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.digite_o_nome_do_posto),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Thin
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                    )
                )

                Text(
                    text = stringResource(R.string.pre_o_da_gasolina), fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(48.dp, 4.dp),
                )

                Spacer(modifier = Modifier.size(0.dp, 16.dp))

                FuelInput(gasText, onChangeText = { gasText = it },
                    stringResource(R.string.gasolina))

                Text(
                    text = stringResource(R.string.pre_o_da_lcool), fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(48.dp, 8.dp),
                )

                Spacer(modifier = Modifier.size(0.dp, 24.dp))

                FuelInput(alcoolText, onChangeText = { alcoolText = it },
                    stringResource(R.string.alcool))

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


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Alcool_or_gasTheme {
        AlcoolOrGas(navController = null, false)
    }
}
