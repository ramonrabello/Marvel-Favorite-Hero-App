package com.github.ramonrabello.favoritehero.data.model

/**
 * Models representation for /v1/public/characters/{characterId}/comics endpoint.
 */
data class ComicDataWrapper(val code: Int, val status: String, val data: ComicDataContainer)
data class ComicDataContainer(val results: List<DetailData>)