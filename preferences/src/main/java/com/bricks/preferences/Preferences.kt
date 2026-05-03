package com.bricks.preferences

import kotlinx.coroutines.flow.Flow

interface Preferences {
    fun <T> getValue(key: Key<T>): T
    fun <T> putValue(key: Key<T>, value: T)
    fun <T> removeKey(key: Key<T>)
    fun clearAll()
    fun <T> asyncGetValue(key: Key<T>): Flow<T>
}