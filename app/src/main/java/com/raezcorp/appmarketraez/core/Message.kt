package com.raezcorp.appmarketraez.core

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.raezcorp.appmarketraez.MainActivity
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.databinding.DialogUnauthorizedBinding
import com.raezcorp.appmarketraez.util.Constants

object Message {
    fun Context.toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


    fun Context.showMessage(message:String): AlertDialog {
        // Implement custom alert - dialog_unauthorized
        val binding = DialogUnauthorizedBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)

        val alertDialog = builder.create()
        binding.tvMessage.text = message

        binding.btnOk.setOnClickListener {
            alertDialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
        }
        return alertDialog
    }
}