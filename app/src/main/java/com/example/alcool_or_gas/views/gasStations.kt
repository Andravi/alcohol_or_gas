package com.example.alcool_or_gas.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.alcool_or_gas.data.Coordinates
import com.example.alcool_or_gas.data.GasStation
import com.example.alcool_or_gas.data.GasStationList
import com.example.alcool_or_gas.ui.composables.FuelInput
import com.example.alcool_or_gas.ui.theme.Alcool_or_gasTheme
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GasStations(navController: NavHostController, add: String, posto: GasStation) {
    val context = LocalContext.current
    val gasStationList = loadStations2(context)




    LaunchedEffect(Unit) { // Muito Bommmmmmmm
        if (add.isNotEmpty()) {
            saveGasStation2(
                context, posto, gasStationList

            )
        }
    }

    var selecionateItem: Int = 0
    var isDialogEditDisplay by remember { mutableStateOf(false) }
    var isDialogInformationDisplay by remember { mutableStateOf(false) }
    var gasText by remember { mutableStateOf("") }
    var alcoolText by rememberSaveable { mutableStateOf("") }



    Scaffold(topBar = {
        TopAppBar(title = { Text("Lista de Postos") })
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(gasStationList.list) { item ->
                Card(
                    onClick = {
                        //Abrir mais informações
                        isDialogInformationDisplay = true
                        selecionateItem = gasStationList.list.indexOf(item)

                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        if (isDialogInformationDisplay) {
                            val pos = gasStationList.list[selecionateItem]
                            DialogInformation(pos,
                                onConfirmation = {
                                    openMaps(context, pos) },
                                onDismissRequest = { isDialogInformationDisplay = false })
                        }

                        if (isDialogEditDisplay) {
                            DialogEdition(
                                onDismissRequest = { isDialogEditDisplay = false },
                                onChangeTextgas = { gasText = it },
                                onChangeTextAlcool = { alcoolText = it },
                                onConfirmation = {
                                    val pos = gasStationList.list[selecionateItem]

                                    if (gasText.isNotEmpty()) {
                                        pos.gasPrice = gasText.toDouble()
                                    }
                                    if (alcoolText.isNotEmpty()) {
                                        pos.alcoolPrice = alcoolText.toDouble()
                                    }

                                    gasStationList.list[selecionateItem] = pos
                                    // Falta salvar
                                    saveGasStationList(context, gasStationList)
                                    Toast.makeText(context, "Editado!", Toast.LENGTH_SHORT).show()
                                },
                                gas = gasText.replace('.', ','),
                                alcool = alcoolText.replace('.', ',')
                            )
                        }

                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Row {
                                    Text(
                                        "Gas: R$ ${item.gasPrice.toString()}",
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                    Text(
                                        "Gas: R$ ${item.alcoolPrice.toString()}",
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }

                            }
                            Row {
                                IconButton(
                                    onClick = {
                                        isDialogEditDisplay = true
                                        selecionateItem = gasStationList.list.indexOf(item)
                                        gasText = item.gasPrice.toString()
                                        alcoolText = item.alcoolPrice.toString()
                                    },
                                ) {
                                    Icon(Icons.Rounded.Edit, "Editar")
                                }
                                IconButton(
                                    onClick = {
                                        deletegasStation(context, item, gasStationList)
                                        gasStationList.list.remove(item)
                                        Toast.makeText(context, "apagado", Toast.LENGTH_SHORT)
                                            .show()
                                        sair(navController)
                                    },
                                ) {
                                    Icon(Icons.Rounded.Delete, "Deletar")
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

fun openMaps(context: Context, item: GasStation) {
    val gmmIntentUri = Uri.parse("geo:${item.coord.lat},${item.coord.lgt}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        setPackage("com.google.android.apps.maps") // Garante que o Maps será usado
    }
    context.startActivity(mapIntent)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DialogEditionPreview() {
    DialogInformation(GasStation(""), onDismissRequest = {}, onConfirmation = {})
}

@Composable
fun DialogEdition(
    gas: String,
    alcool: String,
    onChangeTextgas: (String) -> Unit,
    onChangeTextAlcool: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    var gasText = gas
    var alcoolText = alcool

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                Color.Black,
                contentColor = Color.Black,
                disabledContainerColor = Color.Black,
                disabledContentColor = Color.Black,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Faça os ajustes e aperte em Salvar",
                    modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onSurface,
                )
                FuelInput(gasText, onChangeText = onChangeTextgas, "Gasolina")
                FuelInput(alcoolText, onChangeText = onChangeTextAlcool, "Alcool")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Fechar")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}

@Composable
fun DialogInformation(
    gasStation: GasStation,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                Color.Black,
                contentColor = Color.Black,
                disabledContainerColor = Color.Black,
                disabledContentColor = Color.Black,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Informações Adicionais",
                    modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onSurface,
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Nome:")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,

                                    )
                            ) {
                                append(gasStation.name)
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Preço do Alcool: ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,

                                    )
                            ) {
                                append("R$ ${gasStation.alcoolPrice.toString().replace('.', ',')}")
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Preço da Gasolina: ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,

                                    )
                            ) {
                                append("R$ ${gasStation.gasPrice.toString().replace('.', ',')}")
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Data de Cadastro: ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,

                                    )
                            ) {
                                append(gasStation.criacao)
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Fechar")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Abrir no Maps")
                    }
                }
            }
        }
    }
}


fun sair(nav: NavHostController) {
    nav.navigate("home")
}


fun saveGasStationList(context: Context, list: GasStationList) {
    val dt = ByteArrayOutputStream()
    val oos = ObjectOutputStream(dt);
    oos.writeObject(list);
    val sharedFileName = "gasStationData"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()
    val aux = dt.toString(StandardCharsets.ISO_8859_1.name())
    editor.putString("gasStationList", aux)
    editor.apply()
}


fun deletegasStation(context: Context, gasStation: GasStation, list: GasStationList) {
    list.list.remove(gasStation)
    saveGasStationList(context, list)
}

fun loadStations2(context: Context): GasStationList {
    val sharedFileName = "gasStationData"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    var gasStationlist = GasStationList() // Depois mudar essa forma
    val aux: String = sp.getString("gasStationList", "").toString()
    if (aux.length >= 2) {
        //Convertendo em objeto
        val bis: ByteArrayInputStream = ByteArrayInputStream(aux.toByteArray(Charsets.ISO_8859_1))
        val obi: ObjectInputStream = ObjectInputStream(bis)
        //lendo
        gasStationlist = obi.readObject() as GasStationList
    }
    return gasStationlist
}

fun saveGasStation2(context: Context, gasStation: GasStation, list: GasStationList) {
    list.list.add(gasStation)
    saveGasStationList(context, list)
}


fun loadStations(context: Context, numMax: Int): List<GasStation> {
    val listaGasStation = mutableListOf<GasStation>()
    for (i in 0..numMax) {

//        val sharedFileName = "gasStationData"
//        val sp: SharedPreferences =
//            context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        listaGasStation.add(getGasStationSerializableById(context, i))
    }
    return listaGasStation
}


fun getGasStationSerializableById(context: Context, id: Int): GasStation {
    val sharedFileName = "gasStationData"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    var gasStation =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GasStation("Erro", 0.0, 0.0, Coordinates(41.40338, 2.17403))
        } else {
            TODO("VERSION.SDK_INT < O")
        } // Depois mudar essa forma
    //Lendo bytes serializados
    val aux: String = sp.getString("gasStation_$id", "").toString()
    if (aux.length >= 2) {
        //Convertendo em objeto
        val bis: ByteArrayInputStream = ByteArrayInputStream(aux.toByteArray(Charsets.ISO_8859_1))
        val obi: ObjectInputStream = ObjectInputStream(bis)

        //lendo
        gasStation = obi.readObject() as GasStation
    }
    return gasStation
}


fun loadNumCurrenty(context: Context): Int {
    val sharedFileName = "gasStationData"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val numCurrenty = sp.getInt("numCurrenty", 0)
    return numCurrenty
}

fun saveNumCurrenty(context: Context, numb: Int) {
    val sharedFileName = "gasStationData"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()
    editor.putInt("numCurrenty", numb)
    editor.apply()

}

// Esse é o meu
fun saveGasStation(context: Context, gasStation: GasStation, lenghtNumber: Int) {

    val id = lenghtNumber + 1
    saveNumCurrenty(context, id)
    val dt = ByteArrayOutputStream()
    val oos = ObjectOutputStream(dt);
    oos.writeObject(gasStation);
    val sharedFileName = "gasStationData"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()
    val aux = dt.toString(StandardCharsets.ISO_8859_1.name())
    editor.putString("gasStation_$id", aux)
    editor.apply()
}


//fun saveGasStation(context: Context, gasStation: GasStation) {
//    Log.v("PDM25", "Salvando o posto")
//    val sharedFileName = "lastGasStation"
//    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
//    var editor = sp.edit()
//    editor.putString("nomeDoPosto", gasStation.name)
//    editor.putString("latitude", gasStation.coord.lat.toString())
//    editor.putString("latitude", gasStation.coord.lgt.toString())
//    editor.apply()
//}

//fun getGasStation(context: Context): GasStation {
//    val sharedFileName = "lastGasStation"
//    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
//    var gasStation = GasStation("Posto na Espanha", Coordinates(41.40338, 2.17403))
//    if (sp != null) {
//        gasStation.name = sp.getString("nomeDoPosto", "").toString()
//    }
//    return gasStation
//}
//
//fun saveGasStationSerializable(context: Context, gasStation: GasStation) {
//    Log.v("PDM25", "Salvando o posto serializado")
//    var dt = ByteArrayOutputStream()
//    var oos = ObjectOutputStream(dt);
//    oos.writeObject(gasStation);
//    val sharedFileName = "lastGasStationSer"
//    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
//    var editor = sp.edit()
//    var aux = dt.toString(StandardCharsets.ISO_8859_1.name())
//    Log.v("PDM25", aux)
//    editor.putString("posto1", aux)
//    editor.apply()
//}
//
//fun getGasStationSerializable(context: Context): GasStation {
//    val sharedFileName = "lastGasStationSer"
//    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
//    var gasStation = GasStation("PostoS", Coordinates(41.40338, 2.17403))
//    var aux: String
//    if (sp != null) {
//        //Lendo bytes serializados
//        aux = sp.getString("posto1", "").toString()
//        Log.v("PDM25", aux)
//
//        if (aux.length >= 2) {
//            //Convertendo em objeto
//            var bis: ByteArrayInputStream
//            bis = ByteArrayInputStream(aux.toByteArray(Charsets.ISO_8859_1))
//            var obi: ObjectInputStream
//            obi = ObjectInputStream(bis)
//
//            //lendo
//            gasStation = obi.readObject() as GasStation
//        }
//    }
//    return gasStation
//}

//fun gasStationToJson(gasStation: GasStation): JSONObject {
//    return JSONObject().apply {
//        put("name", gasStation.name)
//        put("lat", gasStation.coord.lat)
//        put("lgt", gasStation.coord.lgt)
//    }
//}
//
//fun jsonToGasStation(json: JSONObject?): GasStation {
//    //Caso o json seja inválido existe um valor default (ternário a seguir)
//    val name = json?.optString("name", "") ?: ""
//    val lat = json?.optDouble("lat", 0.0) ?: 0.0
//    val lgt = json?.optDouble("lgt", 0.0) ?: 0.0
//    return GasStation(name, Coordinates(lat, lgt))
//}
//
//fun stringToJson_Safe(jsonString: String): JSONObject? {
//    return try {
//        JSONObject(jsonString)
//    } catch (e: Exception) {
//        null // Retorna null se a string não for um JSON válido
//    }
//}
//
//fun saveGasStationJSON(context: Context, gasStation: GasStation) {
//    Log.v("PDM25", "Salvando o posto em JSON")
//    val sharedFileName = "lastGasStationJSON"
//    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
//    var editor = sp.edit()
//    val jsonObject = gasStationToJson(gasStation)
//    Log.v("PDM", ": " + jsonObject);
//    editor.putString("gasJSON", jsonObject.toString())
//    editor.apply()
//}
//
//fun getGasStationJSON(context: Context): GasStation {
//    val sharedFileName = "lastGasStationJSON"
//    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
//    lateinit var gasStation: GasStation
//    var aux: String
//    if (sp != null) {
//        //Lendo bytes serializados
//        aux = sp.getString("gasJSON", "").toString()
//        Log.v("PDM25", aux)
//        //lendo
//        gasStation = jsonToGasStation(stringToJson_Safe(aux))
//
//    }
//    return gasStation
//}
//
////Sugestão de métodos a serem usados para a versão final
//fun getListOfGasStation(context: Context): List<GasStation> {
//    val gasStation1 = GasStation("Posto na Espanha", Coordinates(41.40338, 2.17403))
//    val gasStation2 = GasStation("Posto em NY", Coordinates(40.7128, -74.0060))
//    val gasStation3 = GasStation("Posto em Fortaleza", Coordinates(41.40338, 2.17403))
//
//    return listOf(gasStation1, gasStation2, gasStation3)
//}
//
//fun addGasStation(context: Context, gasStation: GasStation) {
//
//}


