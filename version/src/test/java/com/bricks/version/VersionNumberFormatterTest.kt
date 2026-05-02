package com.bricks.version

import org.junit.Assert.assertEquals
import org.junit.Test

class VersionNumberFormatterTest {

    private val basicVersion = VersionNumber(
        major = 1,
        minor = 2,
        patch = 3,
        versionCode = 42,
        buildType = BuildType.PROD,
        buildHash = "a1b2c3d"
    )

    @Test
    fun `classic format produces major dot minor dot patch`() {
        val formatter = VersionNumberFormatter.Builder().build()

        assertEquals("1.2.3", formatter.format(basicVersion))
    }

    @Test
    fun `classic format with custom versionSeparator`() {
        val formatter = VersionNumberFormatter.Builder()
            .versionSeparator("-")
            .build()

        assertEquals("1-2-3", formatter.format(basicVersion))
    }

    @Test
    fun `classic format with preText`() {
        val formatter = VersionNumberFormatter.Builder()
            .preText("v")
            .formatType(VersionFormatType.Custom(listOf(
                VersionPart.PRE_TEXT,
                VersionPart.MAJOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.MINOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.PATCH
            )))
            .build()

        assertEquals("v1.2.3", formatter.format(basicVersion))
    }

    @Test
    fun `classic format with postText`() {
        val formatter = VersionNumberFormatter.Builder()
            .postText("!")
            .formatType(VersionFormatType.Custom(listOf(
                VersionPart.MAJOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.MINOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.PATCH,
                VersionPart.POST_TEXT
            )))
            .build()

        assertEquals("1.2.3!", formatter.format(basicVersion))
    }

    @Test
    fun `classicWithBuildType format produces major dot minor dot patch dash buildType`() {
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.ClassicWithBuildType)
            .build()

        assertEquals("1.2.3-PROD", formatter.format(basicVersion))
    }

    @Test
    fun `classicWithBuildType format with custom extendSeparator`() {
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.ClassicWithBuildType)
            .extendSeparator("_")
            .build()

        assertEquals("1.2.3_PROD", formatter.format(basicVersion))
    }

    @Test
    fun `classicWithBuildType format with null buildType produces empty string`() {
        val version = basicVersion.copy(buildType = null)
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.ClassicWithBuildType)
            .build()

        assertEquals("1.2.3-", formatter.format(version))
    }

    @Test
    fun `classicWithVersionCode format produces major dot minor dot patch dot versionCode`() {
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.ClassicWithVersionCode)
            .build()

        assertEquals("1.2.3.42", formatter.format(basicVersion))
    }

    @Test
    fun `classicWithVersionCode format with null versionCode produces empty string`() {
        val version = basicVersion.copy(versionCode = null)
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.ClassicWithVersionCode)
            .build()

        assertEquals("1.2.3.", formatter.format(version))
    }

    @Test
    fun `custom format with only major and minor`() {
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.Custom(listOf(
                VersionPart.MAJOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.MINOR
            )))
            .build()

        assertEquals("1.2", formatter.format(basicVersion))
    }

    @Test
    fun `custom format with buildHash`() {
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.Custom(listOf(
                VersionPart.MAJOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.MINOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.PATCH,
                VersionPart.EXTEND_SEPARATOR,
                VersionPart.BUILD_HASH
            )))
            .build()

        assertEquals("1.2.3-a1b2c3d", formatter.format(basicVersion))
    }

    @Test
    fun `custom format with null buildHash produces empty string`() {
        val version = basicVersion.copy(buildHash = null)
        val formatter = VersionNumberFormatter.Builder()
            .formatType(VersionFormatType.Custom(listOf(
                VersionPart.MAJOR,
                VersionPart.EXTEND_SEPARATOR,
                VersionPart.BUILD_HASH
            )))
            .build()

        assertEquals("1-", formatter.format(version))
    }

    @Test
    fun `custom format with preText and postText`() {
        val formatter = VersionNumberFormatter.Builder()
            .preText("ver[")
            .postText("]")
            .formatType(VersionFormatType.Custom(listOf(
                VersionPart.PRE_TEXT,
                VersionPart.MAJOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.MINOR,
                VersionPart.VERSION_SEPARATOR,
                VersionPart.PATCH,
                VersionPart.POST_TEXT
            )))
            .build()

        assertEquals("ver[1.2.3]", formatter.format(basicVersion))
    }

    @Test
    fun `builder default versionSeparator is dot`() {
        val formatter = VersionNumberFormatter.Builder().build()

        assertEquals("1.2.3", formatter.format(basicVersion))
    }

    @Test
    fun `builder default formatType is Classic`() {
        val formatter = VersionNumberFormatter.Builder().build()

        // Classic = major.minor.patch, no buildType or versionCode
        assertEquals("1.2.3", formatter.format(basicVersion))
    }
}
