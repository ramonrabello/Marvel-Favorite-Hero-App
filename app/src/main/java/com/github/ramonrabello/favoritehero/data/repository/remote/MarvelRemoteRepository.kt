package com.github.ramonrabello.favoritehero.data.repository.remote

import com.github.ramonrabello.favoritehero.data.model.CharacterDataWrapper
import com.github.ramonrabello.favoritehero.data.model.ComicDataWrapper
import com.github.ramonrabello.favoritehero.data.model.EventDataWrapper
import com.github.ramonrabello.favoritehero.data.model.SeriesDataWrapper
import com.github.ramonrabello.favoritehero.data.model.StoryDataWrapper
import com.github.ramonrabello.favoritehero.network.MarvelApi
import io.reactivex.Maybe
import javax.inject.Inject

/**
 *
 */
class MarvelRemoteRepository @Inject constructor(private val marvelApi: MarvelApi) {

    fun getAllCharacters(offset: Int): Maybe<CharacterDataWrapper> {
        return marvelApi.getAllCharacters(offset)
    }

    fun getCharactersByName(query: String): Maybe<CharacterDataWrapper> {
        return marvelApi.getCharactersByName(query)
    }

    fun getCharacterComics(characterId:Long) : Maybe<ComicDataWrapper>{
        return marvelApi.getCharacterComics(characterId)
    }

    fun getCharacterEvents(characterId:Long) : Maybe<EventDataWrapper>{
        return marvelApi.getCharacterEvents(characterId)
    }

    fun getCharacterStories(characterId:Long) : Maybe<StoryDataWrapper>{
        return marvelApi.getCharacterStories(characterId)
    }

    fun getCharacterSeries(characterId: Long) : Maybe<SeriesDataWrapper>{
        return marvelApi.getCharacterSeries(characterId)
    }
}