package com.example.smsmanage.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.smsmanage.MainActivity
import com.example.smsmanage.R

private const val REQUEST_ALL_PERMISSIONS_CODE = 1234
private const val REQUEST_SMS_PERMISSIONS_CODE = 1111
private const val REQUEST_NOTIFICATION_PERMISSIONS_CODE = 2222
const val TAG = "PERMISSION_MANAGER"
class PermissionManager(private val context: Context): AppCompatActivity() {

    fun checkSmsPermission(): Boolean {
        val permission = Manifest.permission.SEND_SMS
        val res = context.checkSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
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

    fun requestAllPermission(activity: MainActivity) {
        val permissionList = arrayListOf<String>()
        permissionList.add(Manifest.permission.SEND_SMS)
        permissionList.add(Manifest.permission.RECEIVE_SMS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        ActivityCompat.requestPermissions(
            activity,
            permissionList.toTypedArray(),
            REQUEST_ALL_PERMISSIONS_CODE
        )
    }

    fun requestSmsPermission(activity: MainActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS),
            REQUEST_SMS_PERMISSIONS_CODE
        )
    }
    fun requestNotificationPermission(activity: MainActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_NOTIFICATION_PERMISSIONS_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in permissions.indices) {
            Log.i(TAG, "onRequestPermissionsResult: " + grantResults[i])
        }
        if (grantResults.isNotEmpty()){
            if (permissions[0] == Manifest.permission.SEND_SMS && grantResults[0] != PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.desc_sms_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (permissions.last() == Manifest.permission.POST_NOTIFICATIONS && grantResults.last() != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.desc_notification_permission_denied),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}