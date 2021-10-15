package com.raezcorp.appmarketraez.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Usuario(
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("nombres")
    val nombres: String,
    @SerializedName("apellidos")
    val apellidos: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("celular")
    val celular: String,
    @SerializedName("genero")
    val genero: String,
    @SerializedName("nroDoc")
    val nroDoc: String,
    @SerializedName("tipo")
    val tipo: String
)

data class Gender(
    @SerializedName("genero")
    val genero: String,
    @SerializedName("descripcion")
    val descripcion: String,
) {
    override fun toString(): String {
        return descripcion;
    }
}

data class Category(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("cover")
    val cover: String
)

data class Product(
    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("imagenes")
    val imagenes: List<String>,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("codigo")
    val codigo: String,

    @SerializedName("caracteristicas")
    val caracteristicas: String,
):Serializable