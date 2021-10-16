package com.raezcorp.appmarketraez.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raezcorp.appmarketraez.model.shoppingCar

@Dao
interface shoppingCarDao {
    @Insert
    fun insert(carrito: shoppingCar) : Long

    @Update
    fun update(carrito: shoppingCar)

    @Delete
    fun delete(carrito:shoppingCar)

    //where descripcion=:DES
    @Query("select *from shoppingCar order by uuid desc")
    fun getProducts(): LiveData<List<shoppingCar>>

}