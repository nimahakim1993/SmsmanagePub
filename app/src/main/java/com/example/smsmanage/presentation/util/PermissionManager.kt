package com.example.smsmanage.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
class PermissionManager(private val context: Context) {

    fun checkSmsPermission(): Boolean {
        val permission = Manifest.permission.SEND_SMS
        val res = context.checkSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun requestSmsPermission(requestCode: Int) {
        ActivityCompat.requestPermissions(
            context as AppCompatActivity,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS),
            requestCode
        )
    }
}