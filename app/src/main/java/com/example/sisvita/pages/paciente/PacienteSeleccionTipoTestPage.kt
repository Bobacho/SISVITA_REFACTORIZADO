package com.example.sisvita.pages.paciente

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita.components.VolverMenuButton
import com.example.sisvita.data.Test
import com.example.sisvita.services.TestService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun PacienteSeleccionTipoTestPage(navController: NavController){

    val testService: TestService = viewModel()
    val tests by testService.tests.observeAsState()
    LaunchedEffect(Unit) {
        testService.getTest()
        Log.d("PacienteLaunchEffect",tests.toString())
    }
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        if (tests!!.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(0.5f))
        } else {
            Column(modifier = Modifier.fillMaxWidth(0.75f)) {
                tests!!.forEach {
                    Card (modifier =  Modifier.padding(5.dp)){
                        Text(it.nombreTest)
                        Text(it.autorTest)
                        Text(it.descripcionTest)
                    }
                    Button(onClick = {navController.navigate("pacienteTestPage/${it.idTest}")}){
                        Text("Realizar test de ${it.autorTest}")
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                VolverMenuButton(navController, "paciente")
            }
        }
    }
}



