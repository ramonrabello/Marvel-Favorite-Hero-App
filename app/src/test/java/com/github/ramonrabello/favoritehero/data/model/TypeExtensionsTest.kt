package com.github.ramonrabello.favoritehero.data.model

import com.github.ramonrabello.favoritehero.core.ktx.toMD5
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeExtensionsTest {

    @Test
    fun shouldTestIfStringWasConvertedToMD5(){
        val value = "ramon.rabello@gmail.com"
        val expected = "3ae29968fac2807cbbecc31a43e40ca6"
        val actual = value.toMD5()
        assertEquals(expected, actual)
    }
}