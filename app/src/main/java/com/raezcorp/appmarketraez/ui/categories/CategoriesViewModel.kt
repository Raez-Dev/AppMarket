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

    fun getCategories(token: String) {

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    Api.build().getCategories("Bearer $token")
                }

                if(response.isSuccessful){
                    _categories.value = response.body()?.data
                }
                else{
                    //TODO 401
                }

            }catch (ex:Exception){

            }finally {

            }
        }

    }

}