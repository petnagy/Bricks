package com.bricks.version

sealed class VersionFormatType(open val versionParts: List<VersionPart>) {
    data object Classic : VersionFormatType(listOf(
        VersionPart.MAJOR,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.MINOR,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.PATCH
    ))
    data object ClassicWithBuildType : VersionFormatType(listOf(
        VersionPart.MAJOR,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.MINOR,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.PATCH,
        VersionPart.EXTEND_SEPARATOR,
        VersionPart.BUILD_TYPE
    ))
    data object ClassicWithVersionCode : VersionFormatType(listOf(
        VersionPart.MAJOR,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.MINOR,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.PATCH,
        VersionPart.VERSION_SEPARATOR,
        VersionPart.VERSION_CODE
    ))
    data class Custom(override val versionParts: List<VersionPart>): VersionFormatType(versionParts)
}
