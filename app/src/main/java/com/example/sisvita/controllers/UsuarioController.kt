package com.example.sisvita.controllers

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.sisvita.data.Especialista
import com.example.sisvita.data.Paciente
import com.example.sisvita.pages.especialista.EspecialistaEvaluarTestPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.text.Normalizer.Form

class UsuarioController {

    private var db:SQLiteDatabase

    private val SQL_ENTRY = listOf("""
        CREATE TABLE IF NOT EXISTS Especialista(
                   idEspecialista INTEGER AUTO_INCREMENT,
                   nombreEspecialista TEXT,
                   apellidoEspecialista TEXT,
                   dniEspecialista TEXT,
                   especialidad TEXT,
                   celEspecialista TEXT,
                   correoEspecialista TEXT,
                   contraEspecialista TEXT
        );
    """.trimIndent(),
        """
            CREATE TABLE IF NOT EXISTS Paciente(
                idPaciente INTEGER AUTO_INCREMENT,
                nombrePaciente TEXT,
                apellidoPaciente TEXT,
                dniPaciente TEXT,
                distrito TEXT,
                celPaciente TEXT,
                correoPaciente TEXT,
                contraPaciente TEXT
            );
            
        """.trimIndent()
    )
    private val client = OkHttpClient()
    private val baseUrl = "http://192.168.1.73:5000"
    private val methodLoginPaciente = "/Pacientes/v1/login"
    private val methodRegisterPaciente = "/Pacientes/v1/insert"
    private val methodRegisterEspecialista = "/Especialista/v1/insert"
    private val methodLoginEspecialista = "/Especialista/v1/login"
    private val methodGetPacienteIdByDni = "/Paciente/v1/idByDNI"

    init {
        db = SQLiteDatabase.create(null)
        for (s in SQL_ENTRY) {
            db.execSQL(s)
        }
    }
    private fun registerLocalPaciente(paciente:Paciente){
        val content = ContentValues()
        content.put("nombrePaciente",paciente.nombrePaciente)
        content.put("apellidoPaciente",paciente.apellidoPaciente)
        content.put("celPaciente",paciente.celPaciente)
        content.put("dniPaciente",paciente.dniPaciente)
        content.put("distrito",paciente.distrito)
        content.put("correoPaciente",paciente.correoPaciente)
        content.put("contraPaciente",paciente.contraPaciente)
        db.insert("Paciente",null,content)
    }
    private fun registerLocalEspecialista(especialista: Especialista){
        val content = ContentValues()
        content.put("nombreEspecialista",especialista.nombreEspecialista)
        content.put("apellidoEspecialista",especialista.apellidoEspecialista)
        content.put("celEspecialista",especialista.celEspecialista)
        content.put("dniEspecialista",especialista.dniEspecialista)
        content.put("correoEspecialista",especialista.correoEspecialista)
        content.put("especialidad",especialista.especialidad)
        db.insert("Especialista",null,content)
    }
    suspend fun registerEspecialista(especialista:Especialista):Especialista?{
        return withContext(Dispatchers.IO){
            val json = JSONObject()
                .put("nombreEspecialista",especialista.nombreEspecialista)
                .put("apellidoEspecialista",especialista.apellidoEspecialista)
                .put("dniEspecialista",especialista.dniEspecialista)
                .put("especialidad",especialista.especialidad)
                .put("celEspecialista",especialista.celEspecialista)
                .put("correoEspecialista",especialista.correoEspecialista)
                .put("contraEspecialista",especialista.contraEspecialista)
            val mediaType = "application/json".toMediaTypeOrNull()
            val body = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("${baseUrl}${methodRegisterEspecialista}")
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val jsonObject = JSONObject(response.body!!.string())
                val data = jsonObject.getJSONObject("data")
                val result = Especialista(
                    data.getString("nombreEspecialista"),
                    data.getString("apellidoEspecialista"),
                    data.getString("dniEspecialista"),
                    data.getString("especialidad"),
                    data.getString("celEspecialista"),
                    data.getString("correoEspecialista"),
                    data.getString("contraEspecialiasta")
                )
                return@withContext result
            }
            null
        }
    }
    suspend fun loginEspecialista(correo: String,contra: String):Map<String,Especialista?>{
        return withContext(Dispatchers.IO){
            val json = JSONObject()
                .put("correoEspecialista",correo)
                .put("contraEspecialista",contra)
            val mediaType = "application/json".toMediaTypeOrNull()
            val body = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("${baseUrl}${methodLoginEspecialista}")
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val jsonObject = JSONObject(response.body!!.string())
                val data = jsonObject.getJSONObject("data")
                var result: Especialista? = null
                if(data.length() == 7){
                    result = Especialista(
                        data.getString("nombreEspecialista"),
                        data.getString("apellidoEspecialista"),
                        data.getString("dniEspecialista"),
                        data.getString("especialidad"),
                        data.getString("celEspecialidad"),
                        data.getString("correoEspecialidad"),
                        data.getString("contraEspecialidad")
                    )
                    registerLocalEspecialista(result)
                }
                return@withContext mapOf(data.getString("msg") to result)
            }
            emptyMap()
        }
    }
    suspend fun registerPaciente(paciente:Paciente):Paciente?{
        return withContext(Dispatchers.IO){
            val json = JSONObject()
                .put("nombrePaciente",paciente.nombrePaciente)
                .put("apellidoPaciente",paciente.apellidoPaciente)
                .put("dniPaciente",paciente.dniPaciente)
                .put("distrito",paciente.distrito)
                .put("celPaciente",paciente.celPaciente)
                .put("correoPaciente",paciente.correoPaciente)
                .put("contraPaciente",paciente.contraPaciente)
            val mediaType = "application/json".toMediaTypeOrNull()
            val body = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("${baseUrl}${methodRegisterPaciente}")
                .post(body)
                .addHeader("Content-Type","application/json")
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val jsonObject = JSONObject(response.body!!.string())
                val data = jsonObject.getJSONObject("data")
                val result = Paciente(
                    data.getString("nombrePaciente"),
                    data.getString("apellidoPaciente"),
                    data.getString("dniPaciente"),
                    data.getString("distrito"),
                    data.getString("celPaciente"),
                    data.getString("correoPaciente"),
                    data.getString("contraPaciente")
                )
                return@withContext result
            }
            null
        }
    }
    suspend fun loginPaciente(correo:String,contra:String): Map<String, Paciente?> {
        return withContext(Dispatchers.IO){
            val json = JSONObject()
                .put("correoPaciente",correo)
                .put("contraPaciente",contra)
            val mediaType = "application/json".toMediaTypeOrNull()
            val body = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("${baseUrl}${methodLoginPaciente}")
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                var jsonObject = JSONObject(response.body!!.string())
                var paciente:Paciente? = null
                if(jsonObject.getJSONArray("data").getJSONObject(0).length() == 8){
                    val data = jsonObject.getJSONArray("data").getJSONObject(0)
                    paciente = Paciente(
                        data.getString("nombrePaciente"),
                        data.getString("apellidoPaciente"),
                        data.getString("dniPaciente"),
                        data.getString("distrito"),
                        data.getString("celPaciente"),
                        data.getString("correoPaciente"),
                        data.getString("contraPaciente")
                    )
                    registerLocalPaciente(paciente)
                }
                return@withContext mapOf(jsonObject.getString("msg") to paciente)
            }
            emptyMap()
        }
    }

    suspend fun getPacienteIdByDni(dni:String): Int{
        return withContext(Dispatchers.IO){
            val request = Request.Builder()
                .url("${baseUrl}${methodGetPacienteIdByDni}?dni=${dni}")
                .get()
                .build()
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
               val jsonObject = response.body!!.string().replace("\n","").toInt()
               val data = jsonObject
               return@withContext data
            }
            -1
        }
    }

    fun getLocalPaciente(): Paciente {
            Log.d("LocalPaciente","Fetchin data")
            val cursor = db.query("Paciente", arrayOf("*"), null, null, null, null, null)
            Log.d("LocalPaciente",cursor.count.toString())
            var paciente = Paciente()
            while (cursor.moveToNext()) {
                paciente = Paciente(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
                )
            }
            cursor.close()
            return paciente
    }
    fun getLocalEspecialista(): Especialista {
            val cursor = db.query("Especialista", arrayOf("*"), null, null, null, null, null)
            var especialista: Especialista = Especialista()
            while (cursor.moveToNext()) {
                especialista = Especialista(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
                )
            }
            cursor.close()
            return especialista
    }

    companion object{
        private var instance:UsuarioController? = null

        @JvmStatic
        fun getInstance():UsuarioController{
            if(instance == null){
                instance = UsuarioController()
            }
            return instance as UsuarioController
        }
    }
}


