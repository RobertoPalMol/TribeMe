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

    fun getTribeById(id: Int): Tribe? {
        Log.d("ViewModel", "Buscando tribu con ID: $id en lista: ${tribeData.value.map { it.tribuId }}")
        return tribeData.value.find { it.tribuId == id }
    }

    fun createTribe(context: Context, tribe: Tribe, onSuccess: (Tribe) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val createdTribe = RetrofitInstance.getApiService(context).crearTribu(tribe)
                // Actualiza la lista de tribus con la nueva
                _tribeData.value = _tribeData.value + createdTribe
                onSuccess(createdTribe)
            } catch (e: Exception) {
                val errorMsg = "Error al crear la tribu: ${e.localizedMessage}"
                _error.value = errorMsg
                onError(errorMsg)
                Log.e("MyViewModel", errorMsg, e)
            }
        }
    }


}

