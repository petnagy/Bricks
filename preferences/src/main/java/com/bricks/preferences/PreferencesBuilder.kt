package com.bricks.preferences

import android.content.Context

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
        }
    }

    private fun buildSharedPReferences(context: Context, name: String, mode: Int): Preferences {
        val sharedPreferences = context.getSharedPreferences(name, mode)
        return AndroidSharedPreferences(sharedPreferences)
    }
}