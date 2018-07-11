package com.github.ramonrabello.favoritehero.data.repository

import android.arch.lifecycle.LiveData

/**
 * Base interface for Repositories.
 */
interface HeroRepository<T> {
    fun insert(item: T)
    fun loadAll(offset: Int = 0): LiveData<List<T>>
    fun delete(item: T)
    fun searchByName(item: T): LiveData<List<T>>
    fun findById(itemId: Long): T?
}