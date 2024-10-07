package com.example.sisvita.pages.especialista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.sisvita.R
import com.example.sisvita.components.CerrarSesionButton
import com.example.sisvita.components.MenuButton
import com.example.sisvita.components.MenuHeader

@Composable
fun EspecialistaMenuPage(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()
        .background(color = Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        MenuHeader("generico","generico", R.drawable.especialista,
            "Especialista")
        Spacer(Modifier.fillMaxHeight(0.1f))
        MenuButton(name = "Revisar Test"){}
        MenuButton(name = "Mis Horarios") { }
        MenuButton(name = "Test revisados") { }
        MenuButton(name = "Agendar cita") { }
        MenuButton(name = "Mis citas") {}
        Spacer(Modifier.fillMaxHeight(0.3f))
        Row(modifier = Modifier.fillMaxWidth()){
            Spacer(Modifier.fillMaxWidth(0.1f))
            CerrarSesionButton(navController,"especialista")
        }
    }
}
