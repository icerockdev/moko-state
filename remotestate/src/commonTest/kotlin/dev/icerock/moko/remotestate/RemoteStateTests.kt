package dev.icerock.moko.remotestate

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
}
