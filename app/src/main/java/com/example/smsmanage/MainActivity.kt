package com.example.smsmanage

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.smsmanage.data.receiver.MySmsReceiver
import com.example.smsmanage.databinding.ActivityMainBinding
import com.example.smsmanage.presentation.util.MessageBuilder
import com.example.smsmanage.presentation.util.MessageValidator
import com.example.smsmanage.presentation.util.PermissionManager
import com.example.smsmanage.presentation.viewmodel.MainViewModel
import com.example.smsmanage.presentation.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val REQUEST_SMS_PERMISSIONS_CODE = 1111
private const val TAG = "MAIN_ACTIVITY_TAG"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { mainViewModelFactory }
    @Inject
    lateinit var messageBuilder: MessageBuilder
    @Inject
    lateinit var messageValidator: MessageValidator
    @Inject
    lateinit var permissionManager: PermissionManager
    @Inject
    lateinit var mySmsReceiver: MySmsReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestSmsPermission()
        registerMyReceiver()

        binding.apply {
            btnSend.setOnClickListener {
                val phone = etPhone.text.toString()
                val message = etMessage.text.toString()

                if (!messageValidator.validate(phone, message)) return@setOnClickListener
                if (!permissionManager.checkSmsPermission()) {
                    permissionManager.requestSmsPermission(REQUEST_SMS_PERMISSIONS_CODE)
                } else {
                    mainViewModel.sendMessage(phone, message)
                }
            }
        }


    }

    private fun registerMyReceiver() {
        registerReceiver(
            mySmsReceiver,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        )
    }

    private fun checkFields(): Boolean {
        val phone = binding.etPhone.text.toString()
        val message = binding.etMessage.text.toString()
        if (phone.isEmpty()){
            messageBuilder.showToastMessage("mobile number is empty!")
            return false
        }
        if (phone.length < 11){
            messageBuilder.showToastMessage("mobile number should be 11 digits!")
            return false
        }
        if (!phone.startsWith("09")){
            messageBuilder.showToastMessage("mobile number should start with 09!")
            return false
        }
        if (message.isEmpty()){
            messageBuilder.showToastMessage("message can't be empty")
            return false
        }
        return true
    }


    private fun checkSmsPermission(): Boolean {
        val permission = Manifest.permission.SEND_SMS
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