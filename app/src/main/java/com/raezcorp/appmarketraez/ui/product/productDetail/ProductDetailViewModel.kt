package com.raezcorp.appmarketraez.ui.product.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raezcorp.appmarketraez.data.AppDatabase
import com.raezcorp.appmarketraez.model.shoppingCar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel: ViewModel() {
    private val _loader = MutableLiveData<Boolean>()
    val loader : LiveData<Boolean> = _loader

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private val _responseSuccess = MutableLiveData<String>()
    val responseSuccess : LiveData<String> = _responseSuccess

    fun addShoppingCar(_shoppingCar: shoppingCar) {

        _loader.value = true

        viewModelScope.launch {

            try{
                val result = withContext(Dispatchers.IO){
                    AppDatabase.instance?.shoppingCarDao()?.insert(_shoppingCar)
                }

                if(result?.toInt()!! > 0){
                    _responseSuccess.value = "Product added"
                }else{
                    _error.value = "We have a problem while we processed. Try again."
                }

            }catch (ex:Exception){
                _error.value = ex.message
            }finally {
                _loader.value = false
            }

        }

    }

}