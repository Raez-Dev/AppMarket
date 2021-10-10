package com.raezcorp.appmarketraez.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raezcorp.appmarketraez.data.Api
import com.raezcorp.appmarketraez.model.CreateAccountRequest
import com.raezcorp.appmarketraez.model.Gender
import com.raezcorp.appmarketraez.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterViewModel:ViewModel() {

    private var _loader : MutableLiveData<Boolean> = MutableLiveData()
    var loader : LiveData<Boolean> = _loader

    private var _error : MutableLiveData<String> = MutableLiveData()
    var error : LiveData<String> = _error

    private var _genders : MutableLiveData<List<Gender>> = MutableLiveData()
    var genders : LiveData<List<Gender>> = _genders

    private var _usuario : MutableLiveData<Usuario> = MutableLiveData()
    var usuario : LiveData<Usuario> = _usuario

    fun getGenders() {

        // Dispatchers.IO dispatcher to connect data sources
        viewModelScope.launch {

            _loader.value = true

            try {

                val response = withContext(Dispatchers.IO){
                    Api.build().getGen()
                }

                if(response.isSuccessful){

                    response.body()?.data?.let {
                        _genders.value = it
                    }

                }else{
                    _error.value = response.message()
                }

            }catch (ex:Exception){
                _error.value = ex.toString()
            }
            finally {
                _loader.value = false
            }
        }
    }

    fun createAccount(account : CreateAccountRequest){

        viewModelScope.launch {
            _loader.value = true
            try {
                val response = withContext(Dispatchers.IO){
                    Api.build().postCreateAccount(account)
                }
                if(response.isSuccessful){
                    if(response.body()?.success == true){
                        _usuario.value = response.body()?.data
                    }else{
                        _error.value=response.body()?.message
                    }

                }else{
                    _error.value=response.message()
                }
            }
            catch (ex:Exception){
                _error.value=ex.message
            }
            finally {
                _loader.value = false
            }
        }
    }
}