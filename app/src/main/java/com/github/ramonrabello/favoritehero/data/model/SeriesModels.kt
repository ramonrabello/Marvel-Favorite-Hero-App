package com.github.ramonrabello.favoritehero.data.model

/**
 * Models representation for /v1/public/characters/{characterId}/series endpoint.
 */
data class SeriesDataWrapper(val code: Int, val status: String, val data: SeriesDataContainer)
data class SeriesDataContainer(val results: List<DetailData>)