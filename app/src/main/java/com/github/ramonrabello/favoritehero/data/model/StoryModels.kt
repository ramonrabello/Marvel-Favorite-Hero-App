package com.github.ramonrabello.favoritehero.data.model

/**
 * Models representation for /v1/public/characters/{characterId}/series endpoint.
 */
data class StoryDataWrapper(val code: Int, val status: String, val data: StoryDataContainer)
data class StoryDataContainer(val results: List<DetailData>)