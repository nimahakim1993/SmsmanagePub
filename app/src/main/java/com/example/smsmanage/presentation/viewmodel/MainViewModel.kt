package com.example.smsmanage.presentation.viewmodel

import android.app.Application
import android.os.Build
import android.telephony.SmsManager
import androidx.lifecycle.AndroidViewModel

class MainViewModel(private val app: Application): AndroidViewModel(app) {
    fun sendMessage(phone: String, message: String) {
        val smsManager: SmsManager =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                app.getSystemService(SmsManager::class.java)
            else
                SmsManager.getDefault()
        smsManager.sendTextMessage(phone, null, message, null, null)
    }
}