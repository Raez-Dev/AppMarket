package com.raezcorp.appmarketraez.ui.login

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raezcorp.appmarketraez.data.Api
import com.raezcorp.appmarketraez.model.LoginDto
import com.raezcorp.appmarketraez.model.LoginRequest
import com.raezcorp.appmarketraez.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel: ViewModel() {

    // Variable to be observed by LiveData

    private var _loader : MutableLiveData<Boolean> = MutableLiveData()
    var loader : LiveData<Boolean> = _loader

    private var _error : MutableLiveData<String> = MutableLiveData()
    var error : LiveData<String> = _error

    private var _user : MutableLiveData<Usuario> = MutableLiveData()
    var user : LiveData<Usuario> = _user

    private var _token : MutableLiveData<String> = MutableLiveData()
    var token : LiveData<String> = _token


    // In View Models it's better use the ViewModelScope and not with GlobalScope
    //  And you not need use Dispatchers.Main because Main its the default thread

    fun auth(email: String, password: String) {

        viewModelScope.launch() {

            // Indicate enable progress to the view
            _loader.value = true

            try {
                //  Main Thread
                val response = withContext(Dispatchers.IO){
                    // Extra thread
                    Api.build().authenticate(LoginRequest(email,password))
                }
                //  Main Thread
                if(response.isSuccessful){ //200

                    val loginDto = response.body();
                    loginDto?.let {

                        if (loginDto.success){
                            _token.value = loginDto.token
                            _user.value = loginDto.data
                        }else{
                            _error.value = loginDto.message
                        }

                        //Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    }
                }else{ //400,401,500
                    //Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                    _error.value =response.message()
                }
            }catch (ex: Exception){
                //Toast.makeText(requireContext(), ex.message, Toast.LENGTH_LONG).show()
                _error.value =ex.message
            }finally {
                _loader.value = false
            }
        }
    }

    // Sync with the LoginFragment
}