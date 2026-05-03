@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.bricks.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Double

class AndroidSharedPreferences(private val sharedPreferences: SharedPreferences): Preferences {

    private fun SharedPreferences.getDouble(
        key: String,
        defaultValue: kotlin.Double
    ): kotlin.Double {
        return Double.longBitsToDouble(getLong(key, Double.doubleToRawLongBits(defaultValue)))
    }

    private fun SharedPreferences.Editor.putDouble(key: String, value: kotlin.Double) {
        putLong(key, Double.doubleToRawLongBits(value))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getValue(key: Key<T>): T = when(key) {
            is Key.BooleanKey -> sharedPreferences.getBoolean(key.name, key.defaultValue)
            is Key.DoubleKey -> sharedPreferences.getDouble(key.name, key.defaultValue)
            is Key.FloatKey -> sharedPreferences.getFloat(key.name, key.defaultValue)
            is Key.IntKey -> sharedPreferences.getInt(key.name, key.defaultValue)
            is Key.LongKey -> sharedPreferences.getLong(key.name, key.defaultValue)
            is Key.StringKey -> sharedPreferences.getString(key.name, key.defaultValue)
        } as T

    override fun <T> putValue(key: Key<T>, value: T) {
        sharedPreferences.edit {
            when(key) {
                is Key.BooleanKey -> putBoolean(key.name, value as Boolean)
                is Key.DoubleKey -> putDouble(key.name, value as kotlin.Double)
                is Key.FloatKey -> putFloat(key.name, value as Float)
                is Key.IntKey -> putInt(key.name, value as Int)
                is Key.LongKey -> putLong(key.name, value as Long)
                is Key.StringKey -> putString(key.name, value as String)
            }
        }
    }

    override fun <T> removeKey(key: Key<T>) {
        sharedPreferences.edit {
            remove(key.name)
        }
    }

    override fun clearAll() {
        sharedPreferences.edit {
            clearAll()
        }
    }

    override fun <T> asyncGetValue(key: Key<T>): Flow<T> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, prefKey ->
            if (key.name == prefKey) {
                trySend(getValue(key))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        if (sharedPreferences.contains(key.name)) {
            send(getValue(key))
        }
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.buffer(Channel.UNLIMITED)
}