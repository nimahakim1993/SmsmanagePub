package com.example.smsmanage.presentation.viewmodel

import android.app.Application
import android.telephony.SmsManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MainViewModel(private val app: Application): AndroidViewModel(app) {
    fun sendMessage(phone: String, message: String) {
        val smsManager: SmsManager = app.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(phone, null, message, null, null)
    }

    fun receiveMessage(){

    }
}