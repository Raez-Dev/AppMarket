package com.raezcorp.appmarketraez.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raezcorp.appmarketraez.data.Api
import com.raezcorp.appmarketraez.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductsViewModel : ViewModel() {

    private var _error : MutableLiveData<String> = MutableLiveData()
    var error : LiveData<String> = _error

    private var _loader : MutableLiveData<Boolean> = MutableLiveData()
    var loader : LiveData<Boolean> = _loader

    private var _authorize : MutableLiveData<String> = MutableLiveData()
    var authorize : LiveData<String> = _authorize

    private var _products : MutableLiveData<List<Product>> = MutableLiveData()
    var products : LiveData<List<Product>> = _products

    fun getProducts(uuid:String,token:String){

        viewModelScope.launch {
            _loader.value = true
            try {
                    val response = withContext(Dispatchers.IO){
                        Api.build().getProducts("Bearer $token",uuid)
                    }

                    if(response.isSuccessful){
                        _products.value = response.body()?.data
                    }
                    else{

                        if(response.code() == 401){
                            _authorize.value = "Your session was expire"
                        }else{
                            _error.value = response.message()
                        }
                    }
            }catch (ex: Exception){
                _error.value = ex.message
                _loader.value = false
            }finally {
                _loader.value = false
            }
        }
    }
}