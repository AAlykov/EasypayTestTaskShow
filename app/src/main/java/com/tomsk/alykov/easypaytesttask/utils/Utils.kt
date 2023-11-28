package com.tomsk.alykov.easypaytesttask.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

const val BASE_URL = "https://easypay.world/"
object Utils {

    fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp * 1000L)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date = sdf.format(Date(stamp.time))
        return date.toString()
    }

    fun loadSavedToken(context: Context): String {
        val sp = context.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)
        return sp.getString("SAVED_TOKEN", "").toString()
    }

    fun saveToken(context: Context, token: String) {
        val sp = context.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)
        sp.edit().apply {
            putString("SAVED_TOKEN", token)
            apply()
        }
    }

}