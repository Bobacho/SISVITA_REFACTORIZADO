package com.example.sisvita.pages.paciente

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sisvita.data.Respuesta
import com.example.sisvita.services.PreguntaService
import com.example.sisvita.services.UsuarioService

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PacienteTestPage(navController: NavHostController,idTest:String?) {
    val usuarioService: UsuarioService = viewModel()
    val preguntaService : PreguntaService = viewModel()
    val paciente = usuarioService.getPaciente()
    val resultado by preguntaService.resultado.observeAsState()
    val preguntas by preguntaService.preguntas.observeAsState(emptyList())
    val respuestasSeleccionadas = remember{
        mutableStateMapOf<Int, Respuesta>().apply {
            preguntas.forEachIndexed { index, pregunta ->  put(index,Respuesta())}
        }
    }
    var sendResultados by remember {mutableStateOf(false)}
    LaunchedEffect(Unit) {
        preguntaService.getPreguntas(idTest.toString())
    }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (!sendResultados) {

                    if (preguntas.isEmpty()) {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(0.5f)
                        )
                    } else {
                        Column(
                            modifier = Modifier.fillMaxHeight(0.7f)
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp)
                        ) {
                            preguntas!!.forEach { pregunta ->
                                Column(
                                    modifier = Modifier.padding(5.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Pregunta ${pregunta.numPregunta}")
                                    Text(pregunta.enunciadoPreguntaTest)
                                    pregunta.opciones.forEach { opcion ->
                                        Row {
                                            RadioButton(
                                                selected = respuestasSeleccionadas.contains(pregunta.numPregunta) && respuestasSeleccionadas[pregunta.numPregunta]!!.respuesta == opcion.respuestaOpcionTest,
                                                onClick = {
                                                    respuestasSeleccionadas[pregunta.numPregunta] =
                                                        Respuesta(
                                                            pregunta = pregunta.enunciadoPreguntaTest,
                                                            respuesta = opcion.respuestaOpcionTest,
                                                            valorRespuesta = opcion.puntajeOpcionTest
                                                        )
                                                })
                                            Text(opcion.respuestaOpcionTest)
                                        }
                                    }
                                }
                            }
                            Button(onClick = {
                                val idPaciente =
                                    usuarioService.getPacienteIdByDNI(paciente.dniPaciente)
                                preguntaService.sendResultado(
                                    respuestasSeleccionadas.values.toList(),
                                    idTest.toString(), idPaciente.toString()
                                )
                                sendResultados = true
                            }) {
                                Text("Enviar test")
                            }
                        }
                    }

                } else {
                    if (resultado == null) {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(0.5f)
                        )
                    } else {
                        Text(resultado!!.infoResultado)
                        Text(resultado!!.puntajeResultadoTest.toString())
                        Text("Estos resultados no son suficientes, consultar con un especialista")
                    }
                }
            }
    }
}