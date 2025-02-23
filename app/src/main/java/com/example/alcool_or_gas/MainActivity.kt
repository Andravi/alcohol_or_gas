package com.example.alcool_or_gas

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alcool_or_gas.data.Coordinates
import com.example.alcool_or_gas.data.GasStation
import com.example.alcool_or_gas.ui.composables.FuelInput
import com.example.alcool_or_gas.ui.composables.ResButton
import com.example.alcool_or_gas.ui.composables.ResultadoText
import com.example.alcool_or_gas.ui.composables.Switch70Or75
import com.example.alcool_or_gas.ui.composables.TopBar
import com.example.alcool_or_gas.ui.theme.Alcool_or_gasTheme
import com.example.alcool_or_gas.views.AlcoolOrGas
import com.example.alcool_or_gas.views.GasStations

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val check: Boolean = loadConfig(this)
        setContent {
            Alcool_or_gasTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { AlcoolOrGas(navController, check) }
                    composable("listaDePostos/{add}/{posto}/{alcool}/{gas}/{lat}/{long}") { backStackEntry ->

                        val add = backStackEntry.arguments?.getString("add") ?: ""

                        if (add.isNotEmpty()) {


                            val posto = backStackEntry.arguments?.getString("posto") ?: ""
                            var alcool = backStackEntry.arguments?.getString("alcool") ?: ""
                            var gas = backStackEntry.arguments?.getString("gas") ?: ""
                            var lat = backStackEntry.arguments?.getString("lat") ?: ""
                            var long = backStackEntry.arguments?.getString("long") ?: ""

                            if (gas == "" || alcool == "") {
                                gas = "0"
                                alcool = "0"
                            }


                            GasStations(
                                navController,
                                add,
                                GasStation(
                                    posto,
                                    gas.replace(',', '.').toDouble(),
                                    alcool.replace(',', '.').toDouble(),
                                    Coordinates(lat.toDouble(), long.toDouble())
                                )
                            )
                        } else {

                            GasStations(navController, "", GasStation(""))

                        }
                    }
                }

            }
        }
    }

    fun loadConfig(context: Context): Boolean {
        val sharedFileName = "config_Alc_ou_Gas"
        val sp: SharedPreferences =
            context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        val is_75_checked = sp.getBoolean("is_75_checked", false)
        return is_75_checked
    }
}
