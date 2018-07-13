package com.github.ramonrabello.favoritehero.data.model

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [Character] class.
 */
class CharacterTest {

    @Mock
    lateinit var comicList: ComicList

    @Mock
    lateinit var eventList: EventList

    @Mock
    lateinit var storyList: StoryList

    @Mock
    lateinit var seriesList: SeriesList

    @Mock
    lateinit var image: Image

    @Before
    fun beforeTest(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldCheckIfCharacterHasComics(){
        `when`(comicList.items).thenReturn(mutableListOf(ComicSummary("", "")))
        val character = Character(1L, "", "", image, comicList, eventList, storyList, seriesList)
        assertTrue(character.comics.items.isNotEmpty())
    }

    @Test
    fun shouldCheckIfCharacterHasEvents(){
        `when`(eventList.items).thenReturn(mutableListOf(EventSummary("", "", "")))
        val character = Character(1L, "", "", image, comicList, eventList, storyList, seriesList)
        assertTrue(character.events.items.isNotEmpty())
    }

    @Test
    fun shouldCheckIfCharacterHasStories(){
        `when`(storyList.items).thenReturn(mutableListOf(StorySummary("", "", "")))
        val character = Character(1L, "", "", image, comicList, eventList, storyList, seriesList)
        assertTrue(character.stories.items.isNotEmpty())
    }

    @Test
    fun shouldCheckIfCharacterHasSeries(){
        `when`(seriesList.items).thenReturn(mutableListOf(SeriesSummary("", "", "")))
        val character = Character(1L, "", "", image, comicList, eventList, storyList, seriesList)
        assertTrue(character.series.items.isNotEmpty())
    }
}