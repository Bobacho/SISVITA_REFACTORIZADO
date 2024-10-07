package com.example.sisvita.controllers

import android.util.Log
import com.example.sisvita.data.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject


class TestController {
    private val method:String = "/Tests/v1/listar"
    private val client:OkHttpClient = OkHttpClient()
    private val baseUrl = "http://192.168.1.73:5000"
    suspend fun getTests():List<Test> {
        return withContext(Dispatchers.IO) {
            val request: Request = Request.Builder()
                .url("${baseUrl}${method}")
                .get()
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val json = JSONObject(response.body!!.string()).getJSONArray("data")
                val result = ArrayList<Test>()
                for (i in 0..<json.length()) {
                    val jsonObject = json.getJSONObject(i)
                    result.add(
                        Test(
                            jsonObject.getInt("idTest"),
                            jsonObject.getString("autorTest"),
                            jsonObject.getString("descripcionTest"),
                            jsonObject.getString("nombreTest")
                        )
                    )
                }
                return@withContext result
            }
            emptyList()
        }
    }
    companion object{
        private var instance:TestController? = null
        @JvmStatic
        fun getInstance():TestController{
                if(instance == null){
                    instance = TestController()
                }
            return instance as TestController
        }
    }
}