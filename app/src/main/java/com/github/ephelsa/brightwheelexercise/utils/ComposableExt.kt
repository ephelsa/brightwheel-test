package com.github.ephelsa.brightwheelexercise.utils

import androidx.compose.foundation.lazy.LazyListState

/**
 * Helpful extension function to return the index of the last visible item.
 *
 * @return null if is empty.
 */
fun LazyListState.lastVisibleIndex(): Int? {
    val visibleItemsInfo = layoutInfo.visibleItemsInfo

    if (visibleItemsInfo.isEmpty()) {
        return null
    }

    return visibleItemsInfo.last().index
}