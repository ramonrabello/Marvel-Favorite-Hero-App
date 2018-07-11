package com.github.ramonrabello.favoritehero.data.model

/**
 * Models representation for /v1/public/characters/{characterId}/events endpoint.
 */
data class EventDataWrapper(val code: Int, val status: String, val data: EventDataContainer)
data class EventDataContainer(val results: List<DetailData>)