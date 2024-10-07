package com.example.sisvita.services

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sisvita.controllers.TestController
import com.example.sisvita.data.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestService:ViewModel() {
    private val controller = TestController.getInstance()
    private val _tests = MutableLiveData(ArrayList<Test>())
    val tests: LiveData<ArrayList<Test>> get()= _tests
    fun getTest() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){ ArrayList(controller.getTests()) }
            _tests.value = result
        }
    }

}