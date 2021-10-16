package com.raezcorp.appmarketraez.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raezcorp.appmarketraez.model.shoppingCar


@Database(entities = [shoppingCar::class],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    //  Mapping daos
    abstract fun shoppingCarDao() : shoppingCarDao

    //  Static Class
    companion object{

        var instance : AppDatabase? = null

        //Create database
        fun getInstance(context: Context) :  AppDatabase{

            if(instance==null){
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "BDMarket"
                ).build()
            }
            return instance as AppDatabase
        }
    }

}
