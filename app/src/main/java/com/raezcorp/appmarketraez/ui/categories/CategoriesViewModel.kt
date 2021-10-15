package com.raezcorp.appmarketraez.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raezcorp.appmarketraez.data.Api
import com.raezcorp.appmarketraez.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.lang.Exception

class CategoriesViewModel : ViewModel(){
    private var _categories : MutableLiveData<List<Category>> = MutableLiveData()
    var categories : LiveData<List<Category>> = _categories

    private var _error : MutableLiveData<String> = MutableLiveData()
    var error : LiveData<String> = _error

    private var _loader : MutableLiveData<Boolean> = MutableLiveData()
    var loader : LiveData<Boolean> = _loader

    private var _authorize : MutableLiveData<String> = MutableLiveData()
    var authorize : LiveData<String> = _authorize

    fun getCategories(token: String) {

        viewModelScope.launch {
            _loader.value = true
            try {
                val response = withContext(Dispatchers.IO){
                    Api.build().getCategories("Bearer $token")
                }

                if(response.isSuccessful){
                    _categories.value = response.body()?.data
                }
                else{

                    if(response.code() == 401){
                        _authorize.value = "Your session was expire"
                    }else{
                        _error.value = response.message()
                    }
                }

            }catch (ex:Exception){
                _error.value = ex.message
                _loader.value = false
            }finally {
                _loader.value = false
            }
        }

    }

}