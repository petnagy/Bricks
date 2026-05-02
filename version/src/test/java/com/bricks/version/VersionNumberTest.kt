package com.bricks.version

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class VersionNumberTest {

    @Test
    fun `builder parses full version name correctly`() {
        val version = VersionNumber.Builder("2.1.3", 42).build()

        assertEquals(2, version.major)
        assertEquals(1, version.minor)
        assertEquals(3, version.patch)
        assertEquals(42, version.versionCode)
    }

    @Test
    fun `builder parses major only version name`() {
        val version = VersionNumber.Builder("5", 1).build()

        assertEquals(5, version.major)
        assertEquals(0, version.minor)
        assertEquals(0, version.patch)
    }

    @Test
    fun `builder parses major and minor only version name`() {
        val version = VersionNumber.Builder("3.2", 1).build()

        assertEquals(3, version.major)
        assertEquals(2, version.minor)
        assertEquals(0, version.patch)
    }

    @Test
    fun `builder sets buildType correctly`() {
        val version = VersionNumber.Builder("1.0.0", 1)
            .buildType(BuildType.PROD)
            .build()

        assertEquals(BuildType.PROD, version.buildType)
    }

    @Test
    fun `builder sets buildHash correctly`() {
        val version = VersionNumber.Builder("1.0.0", 1)
            .buildHash("a1b2c3d")
            .build()

        assertEquals("a1b2c3d", version.buildHash)
    }

    @Test
    fun `builder sets all optional fields correctly`() {
        val version = VersionNumber.Builder("1.2.3", 99)
            .buildType(BuildType.PROD)
            .buildHash("abc123")
            .build()

        assertEquals(BuildType.PROD, version.buildType)
        assertEquals("abc123", version.buildHash)
        assertEquals(99, version.versionCode)
    }

    @Test
    fun `builder handles invalid major version`() {
        val version = VersionNumber.Builder("invalid.1.0", 1).build()

        assertEquals(0, version.major)
    }

    @Test
    fun `builder handles invalid minor version`() {
        val version = VersionNumber.Builder("1.invalid.0", 1).build()

        assertEquals(0, version.minor)
    }

    @Test
    fun `builder handles invalid patch version`() {
        val version = VersionNumber.Builder("1.0.invalid", 1).build()

        assertEquals(0, version.patch)
    }

    @Test
    fun `builder defaults optional fields to null`() {
        val version = VersionNumber.Builder("1.0.0", 1).build()

        assertNull(version.buildType)
        assertNull(version.buildHash)
    }

    // endregion

    // region isGreaterThen tests

    @Test
    fun `isGreaterThen returns true when major is greater`() {
        val v1 = VersionNumber.Builder("2.0.0", 1).build()
        val v2 = VersionNumber.Builder("1.9.9", 1).build()

        assertTrue(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns false when major is less`() {
        val v1 = VersionNumber.Builder("1.0.0", 1).build()
        val v2 = VersionNumber.Builder("2.0.0", 1).build()

        assertFalse(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns true when minor is greater`() {
        val v1 = VersionNumber.Builder("1.2.0", 1).build()
        val v2 = VersionNumber.Builder("1.1.9", 1).build()

        assertTrue(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns false when minor is less`() {
        val v1 = VersionNumber.Builder("1.1.0", 1).build()
        val v2 = VersionNumber.Builder("1.2.0", 1).build()

        assertFalse(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns true when patch is greater`() {
        val v1 = VersionNumber.Builder("1.0.2", 1).build()
        val v2 = VersionNumber.Builder("1.0.1", 1).build()

        assertTrue(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns false when patch is less`() {
        val v1 = VersionNumber.Builder("1.0.1", 1).build()
        val v2 = VersionNumber.Builder("1.0.2", 1).build()

        assertFalse(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns true when versionCode is greater`() {
        val v1 = VersionNumber.Builder("1.0.0", 10).build()
        val v2 = VersionNumber.Builder("1.0.0", 9).build()

        assertTrue(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns false when versionCode is less`() {
        val v1 = VersionNumber.Builder("1.0.0", 9).build()
        val v2 = VersionNumber.Builder("1.0.0", 10).build()

        assertFalse(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns false when versions are equal`() {
        val v1 = VersionNumber.Builder("1.0.0", 1).build()
        val v2 = VersionNumber.Builder("1.0.0", 1).build()

        assertFalse(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns false when versionCode is null on both`() {
        val v1 = VersionNumber(major = 1, minor = 0, patch = 0, versionCode = null)
        val v2 = VersionNumber(major = 1, minor = 0, patch = 0, versionCode = null)

        assertFalse(v1.isGreaterThen(v2))
    }

    @Test
    fun `isGreaterThen returns true when this versionCode is set and other is null`() {
        val v1 = VersionNumber(major = 1, minor = 0, patch = 0, versionCode = 1)
        val v2 = VersionNumber(major = 1, minor = 0, patch = 0, versionCode = null)

        assertTrue(v1.isGreaterThen(v2))
    }
}
