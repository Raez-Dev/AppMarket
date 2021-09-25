package com.raezcorp.appmarketraez.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Usuario (
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("nombres")
    val nombres:String,
    @SerializedName("apellidos")
    val apellidos:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("celular")
    val celular:String,
    @SerializedName("genero")
    val genero:String,
    @SerializedName("nroDoc")
    val nroDoc:String,
    @SerializedName("tipo")
    val tipo:String
)