package com.example.sisvita.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuButton(name: String, onClick: () -> Unit){
    OutlinedButton(onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.5f)
            .padding(5.dp),
        colors = ButtonColors(contentColor = Color.Black, containerColor = Color.Green,
            disabledContentColor = Color.Red,
            disabledContainerColor = Color.DarkGray)
    ) {
        Text(text = name)
    }
}

@Composable
fun CerrarSesionButton(navController: NavController,tipo:String){
    OutlinedButton(onClick = {
        navController.navigate("loginPage/${tipo}")
    },
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonColors(contentColor = Color.Black, containerColor = Color.Red,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Blue)
    ){
        Text("Cerrar Sesion")
    }
}

@Composable
fun VolverMenuButton(navController: NavController,tipo: String){
    OutlinedButton(onClick = {
        navController.navigate("${tipo}MenuPage")
    },
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonColors(containerColor = Color.Red, contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Blue)
    ){
        Text("Volver al menu")
    }
}