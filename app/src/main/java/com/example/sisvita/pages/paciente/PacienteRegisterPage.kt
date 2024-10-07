package com.example.sisvita.pages.paciente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita.components.RegisterHeader
import com.example.sisvita.data.Paciente
import com.example.sisvita.services.UsuarioService

@Composable
fun PacienteRegisterPage(navController: NavController){
    val usuarioService: UsuarioService = viewModel()
    var nombreUsuario by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var nroCelular by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var distrito by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val distritos by remember { mutableStateOf(listOf("daa","daaa","aea","pipipi")) }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        RegisterHeader("Paciente")
        Text("Nombres")
        TextField(value = nombreUsuario, onValueChange = { nombreUsuario = it })
        Text("Apellidos")
        TextField(value = apellidos, onValueChange = { apellidos = it })
        Text("DNI")
        TextField(value = dni, onValueChange = { dni = it })
        Text("Numero de celular")
        TextField(value = nroCelular, onValueChange = { nroCelular = it })
        Text("Distrito")
        Column {
            var query by remember { mutableStateOf("") }
            var filterList by remember { mutableStateOf(listOf("")) }
            TextField(value = query, onValueChange = {
                query = it
                isSearching = true
                filterList = distritos.filter { it.contains(query, true) }
            })
            if (isSearching) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    filterList.forEach {
                        Button(
                            onClick = {
                                distrito = it
                                isSearching = false
                            },
                            modifier = Modifier.fillMaxWidth(0.75f)
                        ) {
                            Text(text = it)
                        }
                    }
                    isSearching = query.isNotEmpty()
                }
            }
        }
        Text("Correo")
        TextField(value = correo, onValueChange = { correo = it })
        Text("Contrasena")
        TextField(value = password, onValueChange = { password = it })

        Button(onClick = {
            usuarioService.registerPaciente(
                Paciente(
                    nombrePaciente = nombreUsuario,
                    apellidoPaciente = apellidos,
                    contraPaciente = password,
                    dniPaciente = dni,
                    celPaciente = nroCelular,
                    distrito = distrito,
                    correoPaciente = correo
                )
            )
            navController.navigate("loginPage/Paciente")
        }) {
            Text("Registrar paciente")
        }
    }
}