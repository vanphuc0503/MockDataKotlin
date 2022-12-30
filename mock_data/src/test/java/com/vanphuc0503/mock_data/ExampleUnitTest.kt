package com.vanphuc0503.mock_data

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        runBlocking {
            MockData.fakeData<Vanh>()
        }
    }
}

data class Vanh(
    val a: Int,
    val b: String,
    val c: Char
)
