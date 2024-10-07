package com.example.sisvita.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita.R
import com.example.sisvita.data.Especialista
import com.example.sisvita.data.Paciente
import com.example.sisvita.services.UsuarioService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

@Composable
fun LoginPage(navController: NavController, persona: String?){
    val usuarioService:UsuarioService = viewModel()

    val paciente = usuarioService.getPaciente()

    var correo by remember{ mutableStateOf(paciente.correoPaciente)}
    var password by remember { mutableStateOf(paciente.contraPaciente) }

    Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly){
        Image(modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth(0.5f),
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo")
        TextField(
            value = correo.orEmpty(),
            onValueChange = { correo= it },
            placeholder = {
                Text(
                    text= stringResource(R.string.correo_input))
            },
            modifier = Modifier.fillMaxWidth(0.8f)
                .border(width = Dp(3.0f), color = Color.Black,shape = CircleShape),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password.orEmpty(),
            onValueChange = { password = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.password_input))
            },
            modifier = Modifier.fillMaxWidth(0.8f)
                .border(width = Dp(3.0f), color = Color.Black,shape = CircleShape),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(onClick = {
            if(persona == "Paciente"){
                usuarioService.loginPaciente(correo.orEmpty(),password.orEmpty())
            }else{
                usuarioService.loginEspecialista(correo.orEmpty(),password.orEmpty())
            }
            navController.navigate("${persona}MenuPage")
        }) {
            Text(stringResource(R.string.iniciar_sesion))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
          Text(text = stringResource(R.string.descripcion_cuenta),
              fontSize = 2.em)
            TextButton(onClick = {
                navController.navigate("${persona}RegisterPage")
            }, modifier= Modifier.padding(top=Dp(2.0f))) {
                Text(text = stringResource(R.string.register),
                fontSize = 2.em)
            }
        }
    }
}