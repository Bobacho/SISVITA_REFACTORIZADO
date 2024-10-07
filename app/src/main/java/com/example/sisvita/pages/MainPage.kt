package com.example.sisvita.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sisvita.R

@Composable
fun MainPage(navController:NavController){
    Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Image(modifier = Modifier.fillMaxHeight(0.3f).fillMaxWidth(0.5f),
            painter = painterResource(R.drawable.especialista),
            contentDescription = "Especialista")
        Button(onClick = {navController.navigate("loginPage/Especialista")},border = BorderStroke(1.dp,color = Color.Black,),
            colors= ButtonColors(containerColor = Color.Cyan,
                contentColor = Color.Black,
                disabledContainerColor = Color.Green,
                disabledContentColor = Color.White)){
            Text("Especialista")
        }
        Image(modifier = Modifier.fillMaxWidth(800*0.3f/512).fillMaxHeight(800*0.3f/512),
            painter = painterResource(R.drawable.paciente),
            contentDescription = "Paciente")
        Button(onClick = {navController.navigate("loginPage/Paciente")},border = BorderStroke(1.dp,color = Color.Black,),
            colors= ButtonColors(containerColor = Color.Cyan,
                contentColor = Color.Black,
                disabledContainerColor = Color.Green,
                disabledContentColor = Color.White)){
            Text("Paciente")
        }
    }
}