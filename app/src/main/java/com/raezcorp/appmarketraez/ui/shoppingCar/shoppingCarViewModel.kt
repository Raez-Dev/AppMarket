package com.raezcorp.appmarketraez.ui.shoppingCar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raezcorp.appmarketraez.data.AppDatabase
import com.raezcorp.appmarketraez.model.shoppingCar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingCarViewModel : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private val _loader = MutableLiveData<Boolean>()
    val loader : LiveData<Boolean> = _loader

    private val _cart = MutableLiveData< List<shoppingCar>>()
    val cart : LiveData<List<shoppingCar>> = _cart

    private val _deleteSuccess = MutableLiveData<String>()
    val deleteSuccess : LiveData<String> = _deleteSuccess

    val getShoppingCar = AppDatabase?.instance?.shoppingCarDao()?.getProducts()

   /* fun getShoppingCar() {

        viewModelScope.launch {

            _loader.value = true

            try{
                val response = withContext(Dispatchers.IO){
                    AppDatabase.instance?.shoppingCarDao()?.getProducts()
                }

                response?.let{
                    _cart.value = it.value
                }

            }catch (ex:Exception){
                _error.value = ex.message
            }finally {
                _loader.value = false
            }
        }

    }*/

    fun delete(entity: shoppingCar) {

        viewModelScope.launch {

            _loader.value = true

            try{
                val response = withContext(Dispatchers.IO){
                    AppDatabase.instance?.shoppingCarDao()?.delete(entity)
                }

                _deleteSuccess.value = "Eliminated product"

            }catch (ex:Exception){
                _error.value = ex.message
            }finally {
                _loader.value = false
            }
        }
    }


}
