package com.example.smsmanage.presentation.viewmodel

import android.app.Application
import android.content.IntentFilter
import android.os.Build
import android.telephony.SmsManager
import androidx.core.content.ContextCompat.registerReceiver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(private val app: Application): AndroidViewModel(app) {
    private val receiveMessage = MutableLiveData<String?>()
    fun sendMessage(phone: String, message: String) {
        val smsManager: SmsManager =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                app.getSystemService(SmsManager::class.java)
            else
                SmsManager.getDefault()
        smsManager.sendTextMessage(phone, null, message, null, null)
    }

    fun setReceiveMessage(message: String?) {
        receiveMessage.postValue(message)
    }

    val getReceiveMessage : LiveData<String?> get() = receiveMessage
}