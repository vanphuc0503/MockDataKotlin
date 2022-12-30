package com.vanphuc0503.mock_data

import com.vanphuc0503.mock_data.annotations.FakeStringLength
import com.vanphuc0503.mock_data.annotations.FakeStringPool
import com.vanphuc0503.mock_data.annotations.Ignore
import kotlin.random.Random
import kotlin.reflect.typeOf

object MockData {
    inline fun <reified T> fakeData(): T {
        val clazz = T::class.java

        val fakeData: T = clazz.newInstance()

        clazz.declaredFields.forEach { field ->
            if(field.getAnnotation(Ignore::class.java) == null) {
                when(field.type) {
                    typeOf<String>() -> {
                        field.annotations.forEach { anno ->
                            anno.javaClass.run {
                                if(isAssignableFrom(FakeStringPool::class.java)) {
                                    field.set(fakeData, RandomImpl.getRandomString((anno as FakeStringPool).pools))
                                } else {
                                    if(isAssignableFrom(FakeStringLength))
                                }
                            }
                        }

                    }
                    typeOf<Int>() -> {

                    }
                    typeOf<Char>() -> {

                    }
                }
                print(field.type)
            }
        }

        return fakeData
    }
}
