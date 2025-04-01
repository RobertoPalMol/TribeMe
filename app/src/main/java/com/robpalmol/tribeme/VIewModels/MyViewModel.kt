package com.robpalmol.tribeme.VIewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.DataBase.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class MyViewModel : ViewModel() {
    private val _data = MutableStateFlow<List<User>>(emptyList())
    val data: StateFlow<List<User>> = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            try {
                val users = RetrofitInstance.api.getAllUsers() // Llamada a la API
                _data.value = users
            }  catch (e: Exception) {
                _error.value = "No se pudo obtener los datos"
                Log.d("MyDataItem", "Error al obtener los datos: $e")
            }
        }
    }
}
