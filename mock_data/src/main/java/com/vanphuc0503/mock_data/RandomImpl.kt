package com.vanphuc0503.mock_data

import com.vanphuc0503.mock_data.constant.ascii
import kotlin.random.Random
import kotlin.random.nextInt

object RandomImpl {
    private val random = Random(System.currentTimeMillis())

    fun getRandomString(pool: Array<String>): String {
        return pool[random.nextInt(0, pool.size)]
    }

    fun getRandomString(from: Int, to: Int, exactly: Int): String {
        val res: StringBuilder = StringBuilder()
        var exactlyClone = exactly

        exactlyClone = if(exactlyClone == -1) random.nextInt(from, to) else exactlyClone

        repeat(exactlyClone) {
            res.append(
                random.nextInt(ascii).toChar()
            )
        }

        return res.toString()
    }
}