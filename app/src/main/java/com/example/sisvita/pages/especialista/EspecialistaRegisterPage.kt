package com.example.sisvita.pages.especialista

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sisvita.components.RegisterHeader
import com.example.sisvita.data.Especialista
import com.example.sisvita.services.UsuarioService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EspecialistaRegisterPage(navController: NavHostController) {
    val usuarioService: UsuarioService = viewModel()
    var nombreUsuario by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("")}
    var nroCelular by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("")}
    var isSearching by remember { mutableStateOf(false)}
    val especialidadList by remember { mutableStateOf(listOf("daa","daaa","aea","pipipi")) }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        RegisterHeader("Especialista")
        Text("Nombres")
        TextField(value = nombreUsuario, onValueChange = {nombreUsuario = it})
        Text("Apellidos")
        TextField(value = apellidos, onValueChange = {apellidos = it})
        Text("DNI")
        TextField(value = dni, onValueChange = {dni = it})
        Text("Numero de celular")
        TextField(value = nroCelular, onValueChange = {nroCelular = it})
        Text("Correo")
        TextField(value = correo, onValueChange = {correo = it})
        Text("Contrasena")
        TextField(value = password, onValueChange = {password = it})
        Text("Especialidad")
        Column {
            var query by remember {mutableStateOf("")}
            var filterList by remember{ mutableStateOf(listOf(""))}
            TextField(value = query, onValueChange = {
                query = it
                isSearching = true
                filterList = especialidadList.filter{ it.contains(query,true)}
            })
            if(isSearching) {
                Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                    filterList.forEach {
                        Button(onClick = {
                            especialidad = it
                            isSearching = false
                        },
                            modifier = Modifier.fillMaxWidth(0.75f)) {
                            Text(text = it)
                        }
                    }
                    isSearching = query.isNotEmpty()
                }
            }
        }
        Button(onClick = {
            usuarioService.registerEspecialista(
                Especialista(
                    nombreEspecialista = nombreUsuario,
                    apellidoEspecialista = apellidos,
                    correoEspecialista = correo,
                    contraEspecialista = password,
                    dniEspecialista = dni,
                    especialidad = especialidad,
                    celEspecialista = nroCelular
                )
            )
            navController.navigate("loginPage/especialista")
        }) {
            Text("Registrar especialista")
        }
    }
}


