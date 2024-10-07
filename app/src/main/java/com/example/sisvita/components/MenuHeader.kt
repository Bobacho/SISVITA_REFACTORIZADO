package com.example.sisvita.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sisvita.R


@Composable
fun MenuHeader(nombreUsuario:String
               , apellidoUsuario:String
               ,resource : Int
                ,tipo : String){
    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.25f)
        .background(color = Color.Green),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        Image(painter = painterResource(resource),
            contentDescription = "imagen_generica",
            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.5f))
        Column {
            Text(text = nombreUsuario,modifier = Modifier.padding(1.dp))
            Text(text = apellidoUsuario,modifier = Modifier.padding(1.dp))
            Text(text = tipo, modifier = Modifier.padding(1.dp),color = Color.Blue)
        }
    }
}