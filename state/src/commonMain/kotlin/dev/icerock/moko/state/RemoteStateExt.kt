/*
 * Copyright 2026 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.state

import dev.icerock.moko.state.RemoteState.Error
import dev.icerock.moko.state.RemoteState.Loading
import dev.icerock.moko.state.RemoteState.Success
import kotlinx.coroutines.flow.MutableStateFlow

fun <K : Any, T : Any, E : Any> RemoteState<T, E>.mapSuccess(map: (T) -> K): RemoteState<K, E> {
    return when (this) {
        is Success -> Success(map(this.data))
        is Error -> this
        Loading -> Loading
    }
}

fun <K : Any, T : Any, E : Any> RemoteState<T, E>.mapError(map: (E) -> K): RemoteState<T, K> {
    return when (this) {
        is Success -> this
        is Error -> Error(map(this.error))
        Loading -> Loading
    }
}

fun <T : Any, E : Any> RemoteState<T, E>.isLoading(): Boolean = this is Loading

fun <T : Any, E : Any> RemoteState<T, E>.isSuccess(): Boolean = this is Success

fun <T : Any, E : Any> RemoteState<T, E>.isError(): Boolean = this is Error

val <T : Any, E : Any> RemoteState<T, E>.data: T? get() = (this as? Success<T>)?.data

val <T : Any, E : Any> RemoteState<T, E>.error: E? get() = (this as? Error<E>)?.error

/**
 * Attempts to atomically update data in the RemoteState.Success state.
 *
 * @param function lambda that computes new data from the current value
 * @return true if the Success state was updated, false if the state is no longer Success
 */
fun <T : Any, E : Any> MutableStateFlow<RemoteState<T, E>>.tryUpdateSuccess(
    function: (T) -> T
): Boolean {
    while (true) {
        val currentState: Success<T> =
            this.value as? Success<T> ?: return false

        val newState: Success<T> = currentState.copy(
            data = function(currentState.data)
        )
        if (this.compareAndSet(expect = currentState, update = newState)) return true
    }
}
