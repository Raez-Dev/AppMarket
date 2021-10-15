package com.raezcorp.appmarketraez.model

import com.google.gson.annotations.SerializedName

class LoginDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:Usuario,
    @SerializedName("token")
    val token:String
)

data class GenderDto(

    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:List<Gender>,
)

data class CategoriesDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:List<Category>,
)

data class ProductDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:List<Product>
)