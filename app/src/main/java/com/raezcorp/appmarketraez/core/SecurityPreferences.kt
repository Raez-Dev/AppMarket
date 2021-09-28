package com.raezcorp.appmarketraez.core

import android.content.Context
import android.content.SharedPreferences
import android.provider.SyncStateContract
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.raezcorp.appmarketraez.util.Constants

object SecurityPreferences {

    // 1    Encrypt preferences

    fun Context.encryptedPreferences(name:String):SharedPreferences{
        val masterKeyAlias =MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            name,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    // 2    Save Token

    fun saveToken(token:String,encryptedSharedPreferences:SharedPreferences){
        encryptedSharedPreferences.edit().putString(Constants.KEY_TOKEN,token).apply()
    }
    // 3    Get Token

    fun getToken(encryptedSharedPreferences: SharedPreferences):String{
        return encryptedSharedPreferences.getString(Constants.KEY_TOKEN,"") ?: ""
    }
}