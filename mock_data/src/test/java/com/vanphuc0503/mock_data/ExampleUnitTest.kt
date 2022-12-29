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
            print(MockData.fakeData<Vanh>())
        }
    }
}

data class Vanh(
    val a: Int,
    @StringFaker(length = 99) val b: String,
)
