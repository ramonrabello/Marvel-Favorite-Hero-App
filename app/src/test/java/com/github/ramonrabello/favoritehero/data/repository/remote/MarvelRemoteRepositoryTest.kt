package com.github.ramonrabello.favoritehero.data.repository.remote

import com.github.ramonrabello.favoritehero.network.MarvelApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [MarvelRemoteRepository] class.
 */
class MarvelRemoteRepositoryTest {

    @Mock
    lateinit var marvelApi: MarvelApi
    private lateinit var repository: MarvelRemoteRepository

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        repository = MarvelRemoteRepository(marvelApi)
    }

    @Test
    fun `should verify if all characters were retrieved`() {
        repository.getAllCharacters(0)
        verify(marvelApi).getAllCharacters(0)
    }

    @Test
    fun `should verify if characters were retrieved by name`() {
        repository.getCharactersByName("Spider-Man")
        verify(marvelApi).getCharactersByName("Spider-Man")
    }

    @Test
    fun `should verify if all character comics were retrieved`() {
        repository.getCharacterComics(123)
        verify(marvelApi).getCharacterComics(123)
    }

    @Test
    fun `should verify if all character events were retrieved`() {
        repository.getCharacterEvents(123)
        verify(marvelApi).getCharacterEvents(123)
    }

    @Test
    fun `should verify if all character stories were retrieved`() {
        repository.getCharacterStories(123)
        verify(marvelApi).getCharacterStories(123)
    }

    @Test
    fun `should verify if all character series were retrieved`() {
        repository.getCharacterSeries(123)
        verify(marvelApi).getCharacterSeries(123)
    }
}