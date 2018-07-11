package com.github.ramonrabello.favoritehero.data.model

/**
 * Models that are common and used by other models.
 */
data class Image(val path: String, val extension: String) {
    override fun toString(): String {
        return "$path.$extension"
    }
}
data class DetailData(val id: Long, val title: String, val description: String?, val thumbnail: Image?)