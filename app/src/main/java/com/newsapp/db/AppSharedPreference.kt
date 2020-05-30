package com.newsapp.db

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreference private constructor(context: Context) {
    private val sharedPref: SharedPreferences

    companion object {
        private const val PREF_NAME = "news_preferences"
        private var PRIVATE_MODE = 0

        @Volatile
        private var INSTANCE: AppSharedPreference? = null

        fun getInstance(context: Context): AppSharedPreference =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: AppSharedPreference(context)
            }
    }

    init {
        sharedPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }

    fun addToSharedPref(prefName: String, data: Long) {
        with(sharedPref.edit()) {
            putLong(prefName, data)
            apply()
        }
    }

    fun addToSharedPref(prefName: String, data: Int) {
        with(sharedPref.edit()) {
            putInt(prefName, data)
            apply()
        }
    }

    fun getDataFromSharedPref(prefName: String, defaultValue: Long) =
        sharedPref.getLong(prefName, defaultValue)

    fun getDataFromSharedPref(prefName: String, defaultValue: Int) =
        sharedPref.getInt(prefName, defaultValue)

}