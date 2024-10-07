package com.example.sisvita.data

data class Pregunta (
    val idPreguntaTest: Int,
    val idTest: Int,
    val enunciadoPreguntaTest: String,
    val numPregunta: Int,
    val opciones: List<Opcion>
)