package com.bricks.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataStorePreferences(private val dataStore: DataStore<Preferences>): com.bricks.preferences.Preferences {

    @Suppress("UNCHECKED_CAST")
    override fun <T> getValue(key: Key<T>): T = runBlocking {
        when(key) {
            is Key.BooleanKey -> dataStore.data.first()[booleanPreferencesKey(key.name)] ?: key.defaultValue
            is Key.DoubleKey ->  dataStore.data.first()[doublePreferencesKey(key.name)] ?: key.defaultValue
            is Key.FloatKey ->  dataStore.data.first()[floatPreferencesKey(key.name)] ?: key.defaultValue
            is Key.IntKey ->  dataStore.data.first()[intPreferencesKey(key.name)] ?: key.defaultValue
            is Key.LongKey ->  dataStore.data.first()[longPreferencesKey(key.name)] ?: key.defaultValue
            is Key.StringKey ->  dataStore.data.first()[stringPreferencesKey(key.name)] ?: key.defaultValue
        } as T
    }

    override fun <T> putValue(key: Key<T>, value: T) {
        runBlocking {
            dataStore.edit { preferences ->
                when(key) {
                    is Key.BooleanKey -> preferences[booleanPreferencesKey(key.name)] = value as Boolean
                    is Key.DoubleKey -> preferences[doublePreferencesKey(key.name)] = value as Double
                    is Key.FloatKey -> preferences[floatPreferencesKey(key.name)] = value as Float
                    is Key.IntKey -> preferences[intPreferencesKey(key.name)] = value as Int
                    is Key.LongKey -> preferences[longPreferencesKey(key.name)] = value as Long
                    is Key.StringKey -> preferences[stringPreferencesKey(key.name)] = value as String
                }
            }
        }
    }

    override fun <T> removeKey(key: Key<T>) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences.remove(
                    when (key) {
                        is Key.BooleanKey -> booleanPreferencesKey(key.name)
                        is Key.DoubleKey -> doublePreferencesKey(key.name)
                        is Key.FloatKey ->  floatPreferencesKey(key.name)
                        is Key.IntKey -> intPreferencesKey(key.name)
                        is Key.LongKey -> longPreferencesKey(key.name)
                        is Key.StringKey -> stringPreferencesKey(key.name)
                    }
                )
            }
        }
    }

    override fun clearAll() {
        runBlocking {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> asyncGetValue(key: Key<T>): Flow<T> {
        return dataStore.data.map { preference ->
            when(key) {
                is Key.BooleanKey -> preference[booleanPreferencesKey(key.name)] ?: key.defaultValue
                is Key.DoubleKey ->  preference[doublePreferencesKey(key.name)] ?: key.defaultValue
                is Key.FloatKey ->  preference[floatPreferencesKey(key.name)] ?: key.defaultValue
                is Key.IntKey ->  preference[intPreferencesKey(key.name)] ?: key.defaultValue
                is Key.LongKey ->  preference[longPreferencesKey(key.name)] ?: key.defaultValue
                is Key.StringKey ->  preference[stringPreferencesKey(key.name)] ?: key.defaultValue
            } as T
        }
    }
}
