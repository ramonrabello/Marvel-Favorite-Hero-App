package com.github.ramonrabello.favoritehero.core.testing

import android.arch.lifecycle.Observer

/**
 * [Observer] for testing purposes.
 */
class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}