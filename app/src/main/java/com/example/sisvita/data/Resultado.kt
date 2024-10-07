package com.example.sisvita.data

data class Resultado(
    val idPaciente:Int,
    val puntajeResultadoTest:Int,
    val infoResultado:String,
    val fechaResultadoTest: String,
    var revisadoResultadoTest:Boolean = false,
    var revisando:Boolean = false
)