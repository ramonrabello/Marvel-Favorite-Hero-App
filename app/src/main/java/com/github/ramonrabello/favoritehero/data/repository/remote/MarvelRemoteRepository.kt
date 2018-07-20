package com.github.ramonrabello.favoritehero.data.repository.remote

import com.github.ramonrabello.favoritehero.data.model.CharacterDataWrapper
import com.github.ramonrabello.favoritehero.data.model.ComicDataWrapper
import com.github.ramonrabello.favoritehero.data.model.EventDataWrapper
import com.github.ramonrabello.favoritehero.data.model.SeriesDataWrapper
import com.github.ramonrabello.favoritehero.data.model.StoryDataWrapper
import com.github.ramonrabello.favoritehero.network.MarvelApi
import com.github.ramonrabello.favoritehero.network.RequestParams
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Remote repository that used call Marvel API endpoints.
 */
class MarvelRemoteRepository @Inject constructor(private val marvelApi: MarvelApi) {

    fun getAllCharacters(offset: Int): Maybe<CharacterDataWrapper> {
        return marvelApi.getAllCharacters(offset)
    }

    fun getCharactersByName(query: String): Maybe<CharacterDataWrapper> {
        return marvelApi.getCharactersByName(query)
    }

    fun getCharacterComics(characterId: Long): Maybe<ComicDataWrapper> {
        return marvelApi.getCharacterComics(characterId, RequestParams.DETAILS_DATA_LIMIT)
    }

    fun getCharacterEvents(characterId: Long): Maybe<EventDataWrapper> {
        return marvelApi.getCharacterEvents(characterId, RequestParams.DETAILS_DATA_LIMIT)
    }

    fun getCharacterStories(characterId: Long): Maybe<StoryDataWrapper> {
        return marvelApi.getCharacterStories(characterId, RequestParams.DETAILS_DATA_LIMIT)
    }

    fun getCharacterSeries(characterId: Long): Maybe<SeriesDataWrapper> {
        return marvelApi.getCharacterSeries(characterId, RequestParams.DETAILS_DATA_LIMIT)
    }
}