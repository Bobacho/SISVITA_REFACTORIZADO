package com.example.sisvita.services

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sisvita.controllers.PreguntaController
import com.example.sisvita.data.Pregunta
import com.example.sisvita.data.Respuesta
import com.example.sisvita.data.Resultado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreguntaService:ViewModel() {
    private val controller = PreguntaController.getInstance()

    private val _resultado = MutableLiveData<Resultado?>()
    val resultado:LiveData<Resultado?> get() = _resultado

    private val _preguntas = MutableLiveData<ArrayList<Pregunta>>()
    val preguntas: LiveData<ArrayList<Pregunta>> get() = _preguntas

    fun getPreguntas(idTest:String){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){ArrayList(controller.getPreguntas(idTest))}
            _preguntas.value = result
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendResultado(respuestas: List<Respuesta?>,idTest: String,idPaciente:String){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){controller.sendRespuestas(respuestas,idTest,idPaciente)}
            _resultado.value = result
        }
    }
}