package com.github.ramonrabello.favoritehero.core.testing

import android.arch.lifecycle.LiveData


/**
 * Extension function that observes a [LiveData] forever,
 * for testing purposes.
 */
fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}