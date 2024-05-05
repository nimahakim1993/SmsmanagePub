package com.example.smsmanage.presentation.util

import android.content.Context
import android.widget.Toast

class MessageValidator(private val context: Context) {

    fun validate(phone: String, message: String): Boolean {
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

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}