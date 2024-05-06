package com.example.smsmanage.presentation.util

import android.content.Context
import android.widget.Toast

class DataValidator(private val context: Context) {

    fun validateSendData(phone: String, message: String): Boolean {
        if (phone.isEmpty() || message.isEmpty()) {
            showToast("Phone number and message cannot be empty")
            return false
        }
        if (!phone.startsWith("09")) {
            showToast("Phone number should start with 09")
            return false
        }
        if (phone.length != 11) {
            showToast("Phone number should be 11 digits")
            return false
        }
        return true
    }

    fun validateReceiveData(phone: String): Boolean {
        if (phone.isEmpty()) {
            showToast("Phone number cannot be empty")
            return false
        }
        if (!phone.startsWith("+98")) {
            showToast("Phone number should start with +98")
            return false
        }
        if (phone.length != 13) {
            showToast("Phone number should be 13 digits")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}