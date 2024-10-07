package com.example.sisvita.controllers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.sisvita.data.Opcion
import com.example.sisvita.data.Pregunta
import com.example.sisvita.data.Respuesta
import com.example.sisvita.data.Resultado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject


class PreguntaController {
    private val baseUrl:String = "http://192.168.1.73:5000"
    private val method:String = "/PreguntasTests/v1/listarPorIdTest"
    private val methodSendRespuesta= "/ResultadoTests/v1/send"
    private val client = OkHttpClient()

    suspend fun getPreguntas(idTest:String):List<Pregunta>{
        return withContext(Dispatchers.IO){
            val request = Request.Builder()
                .url("${baseUrl}${method}?idTest=${idTest}")
                .get()
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val result = ArrayList<Pregunta>()
                val jsonObject = JSONObject(response.body!!.string()).getJSONArray("data")
                for(i in 0 ..<jsonObject.length()){
                    Log.d("PreguntaController",jsonObject.toString())
                    val opcionesJson = jsonObject.getJSONObject(i).getJSONArray("opciones")
                    val opciones = ArrayList<Opcion>()
                    for(j in 0..<opcionesJson.length()){
                        opciones.add(Opcion(
                            idOpcionTest = opcionesJson.getJSONObject(j).getInt("idOpcionTest"),
                            puntajeOpcionTest = opcionesJson.getJSONObject(j).getInt("puntajeOpcionTest"),
                            respuestaOpcionTest = opcionesJson.getJSONObject(j).getString("respuestaOpcionTest")
                        ))
                    }
                    result.add(Pregunta(
                        idPreguntaTest = jsonObject.getJSONObject(i).getInt("idPreguntaTest"),
                        enunciadoPreguntaTest = jsonObject.getJSONObject(i).getString("enunciadoPreguntaTest"),
                        idTest = jsonObject.getJSONObject(i).getInt("idTest"),
                        numPregunta = jsonObject.getJSONObject(i).getInt("numPregunta"),
                        opciones = opciones
                    ))
                }
                return@withContext result
            }
            emptyList()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun sendRespuestas(respuestas: List<Respuesta?>,idTest:String,idPaciente:String): Resultado? {
        return withContext(Dispatchers.IO){
            val respuestasJson = JSONArray()
            respuestas.forEach{
                val element = JSONObject()
                element.put("pregunta",it!!.pregunta)
                element.put("respuesta",it.respuesta)
                element.put("valorRespuesta",it.valorRespuesta)
                respuestasJson.put(element)
            }
            Log.d("Respuestas",respuestasJson.toString())
            val json = JSONObject()
                .put("respuestas",respuestasJson)
                .put("idTest",idTest)
                .put("idPaciente",idPaciente)
            val mediaType = "application/json".toMediaTypeOrNull()
            val body = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("${baseUrl}${methodSendRespuesta}")
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val jsonObject = JSONObject(response.body!!.string())
                val data = jsonObject.getJSONObject("data")
                val result = Resultado(
                    idPaciente = data.getInt("idPaciente"),
                    puntajeResultadoTest = data.getInt("puntajeResultadoTest"),
                    infoResultado = data.getString("infoResultado"),
                    fechaResultadoTest = data.getString("fechaResultadoTest")
                )
                return@withContext result
            }
            null
        }
    }
    companion object{
        private var instance:PreguntaController? = null
        @JvmStatic
        fun getInstance():PreguntaController{
            if(instance == null){
                instance = PreguntaController()
            }
            return instance as PreguntaController
        }
    }
}