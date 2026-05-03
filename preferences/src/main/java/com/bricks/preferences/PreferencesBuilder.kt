package com.bricks.preferences

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class PreferencesBuilder(private val context: Context) {
    private lateinit var preferencesType: PreferencesType

    private lateinit var prefName: String

    private var mode: Int = Context.MODE_PRIVATE

    fun type(type: PreferencesType) = apply { preferencesType = type }

    fun name(name: String) = apply { prefName = name }

    fun mode(mode: Int) = apply { this.mode = mode }

    fun build(): Preferences {
        if (!::preferencesType.isInitialized) error("Preferences type is not initialized!")
        if (!::prefName.isInitialized) error("No name for preferences")

        return when(preferencesType) {
            PreferencesType.SHARED_PREFERENCES -> buildSharedPReferences(context, prefName, mode)
            PreferencesType.PREFERENCES_DATASTORE -> buildPreferencesDataStore(context, prefName)
        }
    }

    private fun buildSharedPReferences(context: Context, name: String, mode: Int): Preferences {
        val sharedPreferences = context.getSharedPreferences(name, mode)
        return AndroidSharedPreferences(sharedPreferences)
    }

    private fun buildPreferencesDataStore(context: Context, name: String): Preferences {
        val dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(name = name) }
        )
        return DataStorePreferences(dataStore)
    }
}
