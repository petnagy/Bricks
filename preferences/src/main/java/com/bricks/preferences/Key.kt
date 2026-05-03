package com.bricks.preferences

sealed class Key<T>(open val name: String, open val defaultValue: T) {
    data class IntKey(override val name: String, override val defaultValue: Int = 0) : Key<Int>(name, defaultValue)
    data class StringKey(override val name: String, override val defaultValue: String = "") : Key<String>(name, defaultValue)
    data class LongKey(override val name: String, override val defaultValue: Long = 0L) : Key<Long>(name, defaultValue)
    data class FloatKey(override val name: String, override val defaultValue: Float = 0.0F) : Key<Float>(name, defaultValue)
    data class DoubleKey(override val name: String, override val defaultValue: Double = 0.0) : Key<Double>(name, defaultValue)
    data class BooleanKey(override val name: String, override val defaultValue: Boolean = false) : Key<Boolean>(name, defaultValue)
}