package com.vanphuc0503.mock_data.annotations

/**
 * Annotation use for apply range to random int
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class FakeIntRange(
    val start: Int,
    val end: Int
)

/**
 * Annotation use for apply length to random string size
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class FakeStringLength(
    @PositiveInt val from: Int,
    @PositiveInt val to: Int,
    @PositiveInt val exactly: Int = -1
)

/**
 * Annotation use for faking data in the specific group
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class FakeStringPool(
    val pools: Array<String>
)

annotation class PositiveInt

