package com.raezcorp.appmarketraez.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class LoginDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:Usuario?,
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

data class PaymentDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data: String
)


@Entity(tableName = "shoppingCar")
data class shoppingCar(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "uuid")
    val uuid: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "precio")
    val precio: Double,

    @ColumnInfo(name = "cantidad")
    var cantidad: Int,

    @ColumnInfo(name = "imagen")
    val imagen: String,

    @ColumnInfo(name = "codigoCategoria")
    val codigoCategoria: String

)