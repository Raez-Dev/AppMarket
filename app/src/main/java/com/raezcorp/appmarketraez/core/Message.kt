package com.raezcorp.appmarketraez.core

import android.content.Context
import android.widget.Toast

object Message {
    fun Context.toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}