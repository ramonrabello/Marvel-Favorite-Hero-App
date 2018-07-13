package com.github.ramonrabello.favoritehero.data.model

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [CharacterDataWrapper] class.
 */
class CharacterDataWrapperTest {

    @Mock
    lateinit var dataContainer: CharacterDataContainer

    @Before
    fun beforeTest(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldCheckIfHasNoDataContainerItems(){
        `when`(dataContainer.results).thenReturn(emptyList())
        val characterDataWrapper = CharacterDataWrapper(200, "OK", dataContainer)
        assertTrue(characterDataWrapper.data.results.isEmpty())
    }
}