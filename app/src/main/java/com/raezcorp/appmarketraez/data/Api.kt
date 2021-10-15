package com.raezcorp.appmarketraez.data

import com.raezcorp.appmarketraez.model.*
import com.raezcorp.appmarketraez.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// Change class for object
object Api {

    private val interceptor = HttpLoggingInterceptor()
    private val okHttpClient = OkHttpClient.Builder()

    init{
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(interceptor)
    }

    // Full URL https://marketapp2021.herokuapp.com/api/usuarios/login
    //  1.  Create an retrofit instance
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.URL_BASE)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create())     // This line convert json to Kotlin class

    //  2.  Methods to use
    // Create an interface
    interface ApiInterface { //  Define - what do you do?

        @POST("api/usuarios/login")   // Label to define http verb to use
        //  Add suspend to convert to suspend function
        suspend fun authenticate(@Body request: LoginRequest): Response<LoginDto>

        @GET("api/usuarios/obtener-generos")
        suspend fun getGen(): Response<GenderDto>

        @POST("api/usuarios/crear-cuenta")
        suspend fun postCreateAccount(@Body request: CreateAccountRequest): Response<LoginDto>

        @GET("api/categorias")
        suspend fun getCategories(@Header("Authorization" ) authorization:String): Response<CategoriesDto>

        @GET("api/categorias/{categoriaId}/productos")
        suspend fun getProducts(@Header("Authorization" ) authorization:String,@Path("categoriaId") uuid:String): Response<ProductDto>

    }

    // 3. Return instance
    fun build(): ApiInterface {
        /* var httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
         httpClient.addInterceptor(interceptor())
        */
        return builder.build().create(ApiInterface::class.java)
    }

    //  Add interceptor
    /*private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }*/
}