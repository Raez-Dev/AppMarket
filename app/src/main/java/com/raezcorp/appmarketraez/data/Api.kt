package com.raezcorp.appmarketraez.data

import com.raezcorp.appmarketraez.model.LoginDto
import com.raezcorp.appmarketraez.model.LoginRequest
import com.raezcorp.appmarketraez.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Change class for object
object Api {

    // Add companion to transform
    //companion object {

        // Full URL https://marketapp2021.herokuapp.com/api/usuarios/login
        //  1.  Create an retrofit instance
        private val builder : Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())     // This line convert json to Kotlin class

        //  2.  Methods to use
        // Create an interface
        interface ApiInterface { //  Define - what do you do?

            @POST("api/usuarios/login")   // Label to define http verb to use
            //  Add suspend to convert to suspend function
            suspend fun authenticate(@Body request:LoginRequest) : Response<LoginDto>
        }

        // 3. Return instance
        fun build():ApiInterface{
            return  builder.build().create(ApiInterface::class.java)
        }
    //}
}