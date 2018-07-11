package com.github.ramonrabello.favoritehero.network

import com.github.ramonrabello.favoritehero.data.model.CharacterDataWrapper
import com.github.ramonrabello.favoritehero.data.model.ComicDataWrapper
import com.github.ramonrabello.favoritehero.data.model.EventDataWrapper
import com.github.ramonrabello.favoritehero.data.model.SeriesDataWrapper
import com.github.ramonrabello.favoritehero.data.model.StoryDataWrapper
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface to request Marvel API endpoints.
 */
interface MarvelApi {

    @GET("/v1/public/characters")
    fun getAllCharacters(@Query("offset") offset: Int = 0): Maybe<CharacterDataWrapper>

    @GET("/v1/public/characters")
    fun getCharactersByName(@Query("name") query: String): Maybe<CharacterDataWrapper>

    @GET("/v1/public/characters/{character_id}/comics")
    fun getCharacterComics(@Path("character_id") characterId: Long): Maybe<ComicDataWrapper>

    @GET("/v1/public/characters/{character_id}/events")
    fun getCharacterEvents(@Path("character_id") characterId: Long): Maybe<EventDataWrapper>

    @GET("/v1/public/characters/{character_id}/stories")
    fun getCharacterStories(@Path("character_id") characterId: Long): Maybe<StoryDataWrapper>

    @GET("/v1/public/characters/{character_id}/series")
    fun getCharacterSeries(@Path("character_id") characterId: Long): Maybe<SeriesDataWrapper>
}