package com.example.alcool_or_gas.data


import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
data class GasStation(
    var name: String,
    var gasPrice: Double,
    var alcoolPrice: Double,
    val coord: Coordinates,
    var criacao: String=""


): Serializable {
    init {
        val dataHoraAtual = LocalDateTime.now()
        val formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        criacao = dataHoraAtual.format(formatador)
    }


    // Construtor secundário com coordenadas de Fortaleza
    constructor(nome: String) : this(nome,0.0,0.0, Coordinates(41.40338, 2.17403))
    constructor(nome: String, gasPrice: Double, alcoolPrice: Double) : this(nome,gasPrice,alcoolPrice, Coordinates(41.40338, 2.17403))
}