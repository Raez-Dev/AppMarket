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