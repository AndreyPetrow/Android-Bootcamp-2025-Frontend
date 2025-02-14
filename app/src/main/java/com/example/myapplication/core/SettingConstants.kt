package com.example.myapplication.core

object SettingConstants {
    // Для сохранения факта входа в систему.
    const val PREFS_FILE: String = "account"
    const val PREF_ID: String = "id"
    const val PREF_ROLE: String = "role"
    const val PREF_CENTER_NAME: String = "centerName"
    const val ERROR_CENTER_NAME: String = "ERROR CENTER NAME"
    const val ERROR_ID: Long = 1.unaryMinus()
    val DEF_VALUE: Nothing? = null
}
