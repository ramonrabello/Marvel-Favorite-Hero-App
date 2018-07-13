package com.github.ramonrabello.favoritehero.core.ktx

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for TypeExtensions.kt.
 */
class TypeExtensionsTest {

    @Test
    fun shouldTestIfStringWasConvertedToMD5(){
        val value = "ramon.rabello@gmail.com"
        val expected = "3ae29968fac2807cbbecc31a43e40ca6"
        val actual = value.toMD5()
        assertEquals(expected, actual)
    }

    @Test
    fun shouldTestIfStringWasConvertedToHex(){
        val value = "ramon.rabello@gmail.com"
        val expected = "72616d6f6e2e726162656c6c6f40676d61696c2e636f6d"
        val actual = value.toByteArray().toHex()
        assertEquals(expected, actual)
    }
}