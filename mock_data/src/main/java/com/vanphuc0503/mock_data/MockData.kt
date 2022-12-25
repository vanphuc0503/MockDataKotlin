package com.vanphuc0503.mock_data

import android.annotation.SuppressLint
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.json.JSONObject
import java.util.*
import kotlin.random.asKotlinRandom
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

object MockData {
    val moshi: Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().lenient().nullSafe())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val stringLorem = listOf(
        "Lorem Ipsum is simply dummy text of the printing and",
        "Lorem Ipsum has been the industry's",
        "Letraset sheets containing Lorem Ipsum passages",
        "Many desktop publishing packages and web page",
        "Various versions have evolved over the years",
        "sometimes on purpose",
        "There are many variations of passages of Lorem Ipsum available",
        "but the majority have suffered alteration in some form",
        "If you are going to use a passage of Lorem Ipsum",
        "All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary",
        "making this the first true generator on the Internet",
        "It uses a dictionary of over 200 Latin words",
        "combined with a handful of model sentence structures",
        "Lorem Ipsum is therefore always free from repetition",
        "or non-characteristic words etc",
        "The standard chunk of Lorem Ipsum used since the 1500s is reproduced",
        "Contrary to popular belief",
    )

    private const val charString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private val random = Random()

    suspend inline fun <reified T : Any> fakeData(
        data: T,
        times: Int = 1,
        timesRepeat: Int = 1,
        crossinline callback: (List<T?>?) -> Unit
    ) {
        val jsonAdapter = moshi.adapter(T::class.java)
        val listField = T::class.memberProperties
        callback.invoke(convertData(jsonAdapter, listField, data, times, timesRepeat))
    }

    @SuppressLint("CheckResult")
    suspend fun <T : Any> convertData(
        jsonAdapter: JsonAdapter<T>,
        listField: Collection<KProperty1<T, *>>,
        data: T,
        times: Int = 1,
        timesRepeat: Int = 1
    ): List<T?>? {
        val json = jsonAdapter.toJson(data)
        val jsonObject = JSONObject(json)
        try {
            return buildList {
                coroutineScope {
                    // repeat inside the coroutineScope
                    repeat(if (times > 0) times else timesRepeat) {
                        add(async {
                            listField.forEach { field ->
                                val value = when (val type = field.returnType.jvmErasure) {
                                    Int::class -> {
                                        random.nextInt()
                                    }
                                    Long::class -> {
                                        random.nextLong()
                                    }
                                    Double::class -> {
                                        random.nextDouble()
                                    }
                                    String::class -> {
                                        stringLorem[random.asKotlinRandom()
                                            .nextInt(0, stringLorem.size)]
                                    }
                                    Float::class -> {
                                        random.nextFloat()
                                    }
                                    Short::class -> {
                                        random.asKotlinRandom().nextInt()
                                    }
                                    Char::class -> {
                                        charString[random.nextInt(charString.length)]
                                    }
                                    Boolean::class -> {
                                        random.nextBoolean()
                                    }
                                    Date::class -> {
                                        Date(System.currentTimeMillis() - random.nextInt()).formatIsoDate()
                                    }
                                    Byte::class -> {
                                        random.asKotlinRandom().nextInt(
                                            Byte.MIN_VALUE.toInt(),
                                            Byte.MAX_VALUE.toInt()
                                        )
                                    }
                                    else -> {
                                        if (type.isSubclassOf(Enum::class)) {
                                            type.java.enumConstants.run {
                                                get(random.asKotlinRandom().nextInt(0, size))
                                            }
                                        } else {
                                            null
                                        }
                                    }
                                }
                                jsonObject.put(field.name, value)
                            }
                            jsonAdapter.fromJson(jsonObject.toString())
                        })
                    }
                }
            }.awaitAll()
        } catch (ex: Exception) {
            println(ex.message)
            return null
        }
    }
}
