package com.example.smsmanage

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.smsmanage.databinding.ActivityMainBinding

private const val REQUEST_SMS_PERMISSIONS_CODE = 1111
private const val TAG = "MAIN_ACTIVITY_TAG"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestSmsPermission()

        binding.apply {
            btnSend.setOnClickListener {
                if (!checkSmsPermission())
                    requestSmsPermission()
                else{
                    sendMessage()
                }
            }
        }


    }

    private fun sendMessage() {

    }


    private fun checkSmsPermission(): Boolean {
        val permission = Manifest.permission.READ_SMS
        val res = checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }
    private fun requestSmsPermission() {
        val permissionList = arrayListOf<String>()
        permissionList.add(Manifest.permission.SEND_SMS)
        permissionList.add(Manifest.permission.RECEIVE_SMS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        ActivityCompat.requestPermissions(
            this,
            permissionList.toTypedArray(),
            REQUEST_SMS_PERMISSIONS_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
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
                if (permissions[2] == Manifest.permission.POST_NOTIFICATIONS && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
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