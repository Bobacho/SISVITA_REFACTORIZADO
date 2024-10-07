package com.example.sisvita.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sisvita.controllers.UsuarioController
import com.example.sisvita.data.Especialista
import com.example.sisvita.data.Paciente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UsuarioService: ViewModel() {
    private val controller:UsuarioController = UsuarioController.getInstance()

    private val _loginValueEspecialista = MutableLiveData<Map<String,Especialista?>>()
    val loginValueEspecialista:LiveData<Map<String,Especialista?>> get() = _loginValueEspecialista

    fun getPacienteIdByDNI(dni:String):Int{
        return runBlocking {
            controller.getPacienteIdByDni(dni)
        }
    }

    fun getPaciente():Paciente{
        Log.d("PacienteViewModel","Fetching data")
        val result = controller.getLocalPaciente()
        return result
    }
    fun getEspecialista():Especialista{
        val result = controller.getLocalEspecialista()
        return result
    }

    fun loginPaciente(correo:String,contra:String): Map<String, Paciente?> {
        return runBlocking {
            controller.loginPaciente(correo, contra)
        }
    }
    fun loginEspecialista(correo: String,contra: String): Map<String, Especialista?> {
        return runBlocking {
            controller.loginEspecialista(correo,contra)
        }
    }

    fun registerPaciente(paciente: Paciente) {
        viewModelScope.launch{
            val result = withContext(Dispatchers.IO){controller.registerPaciente(paciente)}
        }
    }

    fun registerEspecialista(especialista: Especialista) {
        viewModelScope.launch{
            val result = withContext(Dispatchers.IO){controller.registerEspecialista(especialista)}
        }
    }
}