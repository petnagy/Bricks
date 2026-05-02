package com.bricks.version

import timber.log.Timber

data class VersionNumber(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val versionCode: Int? = null,
    val buildType: BuildType? = null,
    val buildHash: String? = null
) {
    class Builder(private val versionName: String, private val versionCode: Int) {
        private var buildType: BuildType? = null
        private var buildHash: String? = null

        fun buildType(buildType: BuildType) = apply { this.buildType = buildType }

        fun buildHash(buildHash: String) = apply { this.buildHash = buildHash }

        private fun parseVersionNum(number: String, versionPart: String): Int = try {
            number.toInt()
        } catch (e: NumberFormatException) {
            Timber.e(e, "$versionPart parse failed")
            0
        }

        fun build(): VersionNumber {
            val version = versionName.split(".")
            val major = parseVersionNum(number = version[0], versionPart = "Major")
            val minor = if (version.size > 1) {
                parseVersionNum(number = version[1], versionPart = "Minor")
            } else {
                0
            }
            val patch = if (version.size > 2) {
                parseVersionNum(number = version[2], versionPart = "Patch")
            } else {
                0
            }
            return VersionNumber(
                major = major,
                minor = minor,
                patch = patch,
                versionCode = versionCode,
                buildType = buildType,
                buildHash = buildHash
            )
        }
    }

    fun isGreaterThen(otherVersion: VersionNumber): Boolean = compareValuesBy(
        this, otherVersion,
        { it.major },
        { it.minor },
        { it.patch },
        { it.versionCode }
    ) > 0
}
