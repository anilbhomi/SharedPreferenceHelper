package com.aanealoneal.preferencehelper.preference

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.core.content.edit
import com.google.gson.Gson
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object SharedPrefsManager : KoinComponent {
    private val prefs: SharedPreferences by inject()
    private val gson: Gson by inject()

    fun setValue(key: String, value: String) {
        prefs[key] = value
    }

    inline fun <reified T> getValue(key: String): T {
        return when (T::class.java) {
            String::class.java -> getStringValue(key) as T
            else -> throw Exception("Unhandled return type")
        }
    }

    fun getStringValue(key: String): String {
        return prefs[key] ?: ""
    }

    /**
     * Puts a key-value pair in shared prefs if doesn't exists,
     * otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> prefs.edit {
                putString(key, value)
            }
            is Int -> prefs.edit {
                putInt(key, value)
            }
            is Boolean -> prefs.edit {
                putBoolean(key, value)
            }
            is Float -> prefs.edit {
                putFloat(key, value)
            }
            is Long -> prefs.edit {
                putLong(key, value)
            }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * Finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value will take following if [defaultValue] is not specified
     * null for strings,
     * false for bool and
     * -1 for numeric values (int, float, long)
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    class PrefsChangeListener(val codeToExecute: (String?) -> Unit) :
        OnSharedPreferenceChangeListener {

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            codeToExecute(key)
        }
    }
}