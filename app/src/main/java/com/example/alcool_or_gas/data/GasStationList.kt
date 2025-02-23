package com.example.alcool_or_gas.data

import java.io.Serializable

data class GasStationList(
    var list: MutableList<GasStation>
): Serializable {
    // Construtor secundário com coordenadas de Fortaleza
    constructor() : this(mutableListOf<GasStation>())
}