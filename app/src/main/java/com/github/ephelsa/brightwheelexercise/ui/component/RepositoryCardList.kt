package com.github.ephelsa.brightwheelexercise.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.ui.theme.Space

@Composable
fun RepositoryCardList(
    modifier: Modifier = Modifier,
    list: List<RepositoryInformation>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = Space.Large, vertical = Space.Medium)
    ) {
        items(list) {
            RepositoryCard(
                fullName = it.fullName,
                starCount = it.stars,
                topContributor = it.topContributor?.username
            )
            Divider(thickness = Space.Small, color = Color.Transparent)
        }
    }
}