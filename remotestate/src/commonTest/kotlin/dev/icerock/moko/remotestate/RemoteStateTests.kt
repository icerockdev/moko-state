package dev.icerock.moko.remotestate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RemoteStateTests {
    @Test
    fun testIsLoading() {
        assertTrue(RemoteState.Loading.isLoading())
    }

    @Test
    fun testSuccess() {
        val success = RemoteState.Success("data")
        assertTrue(success.isSuccess())
        assertEquals("data", success.data)
    }

    @Test
    fun testError() {
        val error = RemoteState.Error(Exception("test"))
        assertFalse(error.isSuccess())
        assertTrue(error is RemoteState.Error)
    }

    @Test
    fun testMapSuccess() {
        val state = RemoteState.Success(5)
        val mapped = state.mapSuccess { it * 2 }
        assertEquals(RemoteState.Success(10), mapped)
    }

    @Test
    fun testMapError() {
        val state = RemoteState.Error("error")
        val mapped = state.mapError { it.length }
        assertEquals(RemoteState.Error(5), mapped)
    }

    @Test
    fun testDataProperty() {
        val success = RemoteState.Success("test")
        assertEquals("test", success.data)

        val loading = RemoteState.Loading
        assertNull(loading.data)

        val error = RemoteState.Error(Exception("test"))
        assertNull(error.data)
    }

    @Test
    fun testTryUpdateSuccessError() {
        val flow: MutableStateFlow<RemoteState<Int, String>> =
            MutableStateFlow(RemoteState.Error("error"))
        val updated = flow.tryUpdateSuccess { it * 2 }
        assertFalse(updated)
    }

    @Test
    fun testTryUpdateSuccessLoading() {
        val flow: MutableStateFlow<RemoteState<Int, String>> = MutableStateFlow(RemoteState.Loading)
        val updated = flow.tryUpdateSuccess { it * 2 }
        assertFalse(updated)
    }

    @Test
    fun testTryUpdateSuccessSuccess() {
        val flow: MutableStateFlow<RemoteState<Int, String>> =
            MutableStateFlow(RemoteState.Success(5))
        val updated = flow.tryUpdateSuccess { it * 2 }
        assertTrue(updated)
        assertEquals(RemoteState.Success(10), flow.value)
    }
}
