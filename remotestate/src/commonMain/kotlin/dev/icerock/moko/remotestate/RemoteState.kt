/*
 * Copyright 2026 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.remotestate

/**
 * Implemented as a class, not an interface, so iOS can provide a hashable implementation.
 * Types are intentionally non-nullable for iOS so the compiler does not expect nulls everywhere.
 */
sealed class RemoteState<out T : Any, out E : Any> {
    data object Loading : RemoteState<Nothing, Nothing>()
    data class Success<T : Any>(val data: T) : RemoteState<T, Nothing>()
    data class Error<E : Any>(
        val error: E,
    ) : RemoteState<Nothing, E>()
}
