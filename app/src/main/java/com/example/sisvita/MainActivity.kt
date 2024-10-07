package com.example.sisvita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sisvita.pages.LoginPage
import com.example.sisvita.pages.MainPage
import com.example.sisvita.pages.especialista.EspecialistaMenuPage
import com.example.sisvita.pages.especialista.EspecialistaRegisterPage
import com.example.sisvita.pages.paciente.PacienteCitasPage
import com.example.sisvita.pages.paciente.PacienteContactarEspecialistaPage
import com.example.sisvita.pages.paciente.PacienteMenuPage
import com.example.sisvita.pages.paciente.PacienteRecursosEducativosPage
import com.example.sisvita.pages.paciente.PacienteRegisterPage
import com.example.sisvita.pages.paciente.PacienteSeleccionTipoTestPage
import com.example.sisvita.pages.paciente.PacienteTestPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController= navController,startDestination = "mainPage"){
                composable("mainPage") { MainPage(navController) }
                composable("loginPage/{persona}") { LoginPage(navController,
                    it.arguments!!.getString("persona"))}
                /* ESPECIALISTAS */
                composable("especialistaRegisterPage"){ EspecialistaRegisterPage(navController) }
                composable("especialistaMenuPage"){ EspecialistaMenuPage(navController)}

                /* PACIENTES */
                composable("pacienteRegisterPage"){PacienteRegisterPage(navController)}
                composable("pacienteMenuPage"){ PacienteMenuPage(navController) }
                composable("pacienteSeleccionarTestPage"){ PacienteSeleccionTipoTestPage(navController) }
                composable("pacienteTestRealizadosPage"){ PacienteSeleccionTipoTestPage(navController) }
                composable("pacienteCitasPage"){PacienteCitasPage(navController)}
                composable("pacienteRecursosEducativosPage"){ PacienteRecursosEducativosPage(navController) }
                composable("pacienteTestPage/{idTest}"){ PacienteTestPage(navController,
                    it.arguments!!.getString("idTest"))}
                composable("pacienteContactactarEspecialistaPage"){ PacienteContactarEspecialistaPage(navController) }
            }
        }
    }
}
