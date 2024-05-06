package com.example.smsmanage.data.info

import android.content.Context
import android.content.SharedPreferences
import com.example.smsmanage.Constants

class InfoHelper(private val context: Context) {
    private var dataSharePref: SharedPreferences = context.getSharedPreferences("DATA_INFO", Context.MODE_PRIVATE)

    fun setNumberReceive(value: String){
        dataSharePref.edit().putString(Constants.PARAM_NUMBER_RECEIVE, value).apply()
    }
    fun getNumberReceive(): String{
        return dataSharePref.getString(Constants.PARAM_NUMBER_RECEIVE, "")!!
    }
}