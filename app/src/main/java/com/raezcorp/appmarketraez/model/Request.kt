package com.raezcorp.appmarketraez.model

data class LoginRequest (val email:String,val password:String,val firebaseToken:String = "")