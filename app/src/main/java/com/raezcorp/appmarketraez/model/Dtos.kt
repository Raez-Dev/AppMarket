package com.raezcorp.appmarketraez.model

import com.google.gson.annotations.SerializedName

class LoginDto(
    @SerializedName("succes")
    val succes:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:Usuario,
    @SerializedName("token")
    val token:String
)