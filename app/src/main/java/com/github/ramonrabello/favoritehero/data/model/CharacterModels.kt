package com.github.ramonrabello.favoritehero.data.model

/**
 * Models representation for /v1/public/characters API endpoint.
 */
data class CharacterDataWrapper(val code: Int, val status: String, val data: CharacterDataContainer)

data class CharacterDataContainer(val results: List<Character>)
data class Character(val id: Long, val name: String,
                     val description: String?,
                     val thumbnail: Image,
                     val comics: ComicList,
                     val events: EventList,
                     val stories: StoryList,
                     val series: SeriesList)

data class ComicList(val available: Int, val collectionURI: String, val items: List<ComicSummary>)
data class ComicSummary(val resourceURI: String, val name: String)
data class StoryList(val available: Int, val collectionURI: String, val items: List<StorySummary>)
data class StorySummary(val resourceURI: String, val name: String, val type: String)
data class EventList(val available: Int, val collectionURI: String, val items: List<EventSummary>)
data class EventSummary(val resourceURI: String, val name: String, val type: String)
data class SeriesList(val available: Int, val collectionURI: String, val items: List<SeriesSummary>)
data class SeriesSummary(val resourceURI: String, val name: String, val type: String)