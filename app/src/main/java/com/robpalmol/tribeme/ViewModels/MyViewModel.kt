package com.robpalmol.tribeme.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.User
import com.robpalmol.tribeme.DataBase.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _userData = MutableStateFlow<List<User>>(emptyList())
    val userData: StateFlow<List<User>> = _userData

    private val _tribeData = MutableStateFlow<List<Tribe>>(emptyList())
    val tribeData: StateFlow<List<Tribe>> = _tribeData


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getAllUsers(context: Context) {
        viewModelScope.launch {
            try {
                val users = RetrofitInstance.getApiService(context).getAllUsers()
                _userData.value = users
            } catch (e: Exception) {
                _error.value = "No se pudo obtener los usuarios: ${e.localizedMessage}"
                Log.d("MyViewModel", "Error al obtener los usuarios: $e")
            }
        }
    }

    fun getAllTribes(context: Context) {
        viewModelScope.launch {
            try {
                val tribes = RetrofitInstance.getApiService(context).getAllTribes()
                _tribeData.value = tribes
            } catch (e: Exception) {
                _error.value = "No se pudo obtener las tribus: ${e.localizedMessage}"
                Log.d("MyViewModel", "Error al obtener las tribus: $e")
            }
        }
    }
}

