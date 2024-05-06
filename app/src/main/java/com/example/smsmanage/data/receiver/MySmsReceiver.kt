package com.example.smsmanage.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.example.smsmanage.data.info.InfoHelper

const val TAG = "MY_SMS_RECEIVER"
class MySmsReceiver(private val infoHelper: InfoHelper): BroadcastReceiver() {

    private var onSmsReceived : ((String) -> Unit)?= null

    fun onSmsReceivedListener(listener: (String) -> Unit){
        onSmsReceived = listener
    }

    override fun onReceive(context: Context?, intent: Intent) {
        val smsMap = getMessage(intent)
        val numberToReceive = infoHelper.getNumberReceive()
        smsMap.forEach { (phone, message) ->
            if (phone == numberToReceive) {
                Log.i(TAG, "phone: $phone msg: $message")
                onSmsReceived?.let {
                    it(message!!)
                }
            }
        }
    }

    private fun getMessage(intent: Intent): Map<String, String?> {
        val map: MutableMap<String, String?> = HashMap()
        val bundle = intent.extras ?: return map
        val pdus = bundle["pdus"] as Array<*>?
        val messages = arrayOfNulls<SmsMessage>(
            pdus!!.size
        )
        for (i in pdus.indices) {
            val format = bundle.getString("format")
            messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)
            if (map.containsKey(messages[i]!!.displayOriginatingAddress)) {
                var body = map[messages[i]!!.displayOriginatingAddress]
                body += messages[i]!!.messageBody
                map[messages[i]!!.displayOriginatingAddress] = body
            } else
                map[messages[i]!!.displayOriginatingAddress] = messages[i]!!.messageBody
        }
        return map
    }
}