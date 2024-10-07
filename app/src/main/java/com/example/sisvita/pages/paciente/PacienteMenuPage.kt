package com.example.sisvita.pages.paciente

import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sisvita.R
import com.example.sisvita.components.CerrarSesionButton
import com.example.sisvita.components.MenuButton
import com.example.sisvita.components.MenuHeader
import com.example.sisvita.data.Paciente
import com.example.sisvita.services.UsuarioService


@Composable
fun PacienteMenuPage(navController: NavHostController) {
    val usuarioService: UsuarioService = viewModel ()

    val paciente = usuarioService.getPaciente()

    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()
        .background(color = Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        MenuHeader(paciente.nombrePaciente,paciente.apellidoPaciente,R.drawable.paciente,"Paciente")
        Spacer(Modifier.fillMaxHeight(0.1f))
        MenuButton(name = "Realizar Test", onClick = {navController.navigate("pacienteSeleccionarTestPage")})
        MenuButton(name = "Ver Test Realizados", onClick = { navController.navigate("pacienteTestRealizadosPage")})
        MenuButton(name = "Mis Citas", onClick =  {navController.navigate("pacienteCitasPage") })
        MenuButton(name = "Recursos educativos", onClick = {navController.navigate("pacienteRecursosEducativosPage")})
        MenuButton(name = "Contactar especialistas", onClick = {navController.navigate("pacienteContactarEspecialistaPage")})
        Spacer(Modifier.fillMaxHeight(0.3f))
        Row(modifier = Modifier.fillMaxWidth()){
            Spacer(Modifier.fillMaxWidth(0.1f))
            CerrarSesionButton(navController,"paciente")
        }
    }
}

