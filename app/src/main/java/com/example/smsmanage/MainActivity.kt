package com.example.smsmanage

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.smsmanage.data.info.InfoHelper
import com.example.smsmanage.data.receiver.MySmsReceiver
import com.example.smsmanage.databinding.ActivityMainBinding
import com.example.smsmanage.presentation.util.MessageBuilder
import com.example.smsmanage.presentation.util.DataValidator
import com.example.smsmanage.presentation.util.NotificationManager
import com.example.smsmanage.presentation.util.PermissionManager
import com.example.smsmanage.presentation.viewmodel.MainViewModel
import com.example.smsmanage.presentation.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


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
    lateinit var dataValidator: DataValidator
    @Inject
    lateinit var permissionManager: PermissionManager
    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var infoHelper: InfoHelper
    @Inject
    lateinit var mySmsReceiver: MySmsReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionManager.requestAllPermission(this)
        registerMyReceiver()


        binding.apply {
            btnSend.setOnClickListener {
                val phone = etPhone.text.toString()
                val message = etMessage.text.toString()

                if (!dataValidator.validateSendData(phone, message))
                    return@setOnClickListener
                if (!permissionManager.checkSmsPermission()) {
                    permissionManager.requestSmsPermission(this@MainActivity)
                } else {
                    mainViewModel.sendMessage(phone, message)
                }
            }
            btnSubmitReceiveNumber.setOnClickListener {
                val receivePhone = etReceiveNumber.text.toString()
                if (!dataValidator.validateReceiveData(receivePhone))
                    return@setOnClickListener
                else {
                    saveNumberReceive(receivePhone)
                }
            }


            saveNumberReceive(infoHelper.getNumberReceive())
        }

        mySmsReceiver.onSmsReceivedListener {
            if (!permissionManager.checkNotificationPermission()) {
                permissionManager.requestNotificationPermission(this)
            }
            else if (infoHelper.getNumberReceive().isEmpty()){
                messageBuilder.showToastMessage("to receive message please first input number")
            }
            else
//                notificationManager.notifyMessage(it)

            //use view model to message because if there were some fragments that need to this data viewModel is best practice
                mainViewModel.setReceiveMessage(it)
        }

        mainViewModel.getReceiveMessage.observe(this){ message ->
            if (message == null)
                return@observe
            notificationManager.notifyMessage(message)
            mainViewModel.setReceiveMessage(null)
        }



    }

    private fun saveNumberReceive(receivePhone: String) {
        if (receivePhone.isEmpty())
            return
        infoHelper.setNumberReceive(receivePhone)
        binding.etReceiveNumber.setText(receivePhone)
        binding.etReceiveNumber.clearFocus()
        binding.btnSubmitReceiveNumber.text = getString(R.string.str_edit)
    }

    private fun registerMyReceiver() {
        registerReceiver(
            mySmsReceiver,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mySmsReceiver)
    }




}