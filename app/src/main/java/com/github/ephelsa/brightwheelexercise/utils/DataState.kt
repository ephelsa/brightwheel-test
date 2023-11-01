package com.github.ephelsa.brightwheelexercise.utils

/**
 * Hold the possible states that the data could take.
 */
sealed class DataState<T>(
    val contentReady: T? = null,
    val exception: Exception? = null,
    val errorMessage: String? = null,
) {
    /**
     * The initial state.
     */
    class Initialized<T> : DataState<T>()

    /**
     * When the data is being fetched.
     */
    class Loading<T> : DataState<T>()

    /**
     * When some [ContentReady] is being updated.
     */
    data class UpdatingContent<T>(val previousContent: T?) :
        DataState<T>(contentReady = previousContent)

    /**
     * When the content is ready to be used.
     */
    data class ContentReady<T>(val content: T) : DataState<T>(contentReady = content)
}