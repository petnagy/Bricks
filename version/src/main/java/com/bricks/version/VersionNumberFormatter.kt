package com.bricks.version

class VersionNumberFormatter private constructor(
    private val versionSeparator: String = ".",
    private val extendSeparator: String = "-",
    private val preText: String = "",
    private val postText: String = "",
    private val formatType: VersionFormatType = VersionFormatType.Classic
) {

    class Builder {
        private var versionSeparator: String = "."
        private var extendSeparator: String = "-"
        private var preText: String = ""
        private var postText: String = ""
        private var formatType: VersionFormatType = VersionFormatType.Classic

        fun versionSeparator(separator: String) = apply { versionSeparator = separator }
        fun extendSeparator(separator: String) = apply { extendSeparator = separator }
        fun preText(separator: String) = apply { preText = separator }
        fun postText(separator: String) = apply { postText = separator }
        fun formatType(type: VersionFormatType) = apply { formatType = type }

        fun build() = VersionNumberFormatter(
            versionSeparator = versionSeparator,
            extendSeparator = extendSeparator,
            preText = preText,
            postText = postText,
            formatType = formatType
        )
    }

    fun format(version: VersionNumber): String = formatType.versionParts.joinToString(separator = "") {
        when (it) {
            VersionPart.MAJOR -> version.major.toString()
            VersionPart.MINOR -> version.minor.toString()
            VersionPart.PATCH -> version.patch.toString()
            VersionPart.BUILD_TYPE -> version.buildType?.toString() ?: ""
            VersionPart.VERSION_CODE -> version.versionCode?.toString() ?: ""
            VersionPart.BUILD_HASH -> version.buildHash ?: ""
            VersionPart.VERSION_SEPARATOR -> versionSeparator
            VersionPart.PRE_TEXT -> preText
            VersionPart.POST_TEXT -> postText
            VersionPart.EXTEND_SEPARATOR -> extendSeparator
        }
    }

}
