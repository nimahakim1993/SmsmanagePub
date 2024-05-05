package com.example.smsmanage.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
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

    fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
            true
        else {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val res = context.checkSelfPermission(permission)
            res == PackageManager.PERMISSION_GRANTED
        }
    }


    fun requestNotificationPermission(requestCode: Int) {
        ActivityCompat.requestPermissions(
            context as AppCompatActivity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            requestCode
        )
    }
}