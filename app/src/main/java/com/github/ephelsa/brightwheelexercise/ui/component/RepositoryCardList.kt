package com.github.ephelsa.brightwheelexercise.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.ui.theme.Space
import com.github.ephelsa.brightwheelexercise.utils.ScopedCallback
import com.github.ephelsa.brightwheelexercise.utils.lastVisibleIndex
import kotlinx.coroutines.CoroutineScope

@Composable
fun RepositoryCardList(
    modifier: Modifier = Modifier,
    list: List<RepositoryInformation>,
    listState: LazyListState,
    onLimitReached: ScopedCallback<CoroutineScope>,
    isLoading: Boolean
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(horizontal = Space.Large, vertical = Space.Medium)
    ) {
        items(list) { item ->
            RepositoryCard(
                fullName = item.fullName,
                starCount = item.stars,
                topContributor = item.topContributor?.username
            )
            Divider(thickness = Space.Small, color = Color.Transparent)
        }

        item {
            if (listState.lastVisibleIndex() == list.size) {
                LaunchedEffect(list) {
                    onLimitReached()
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}