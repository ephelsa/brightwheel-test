package com.github.ephelsa.brightwheelexercise.ui.screen

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.ephelsa.brightwheelexercise.ui.activity.MainViewModel
import com.github.ephelsa.brightwheelexercise.ui.template.FeedTemplate
import com.github.ephelsa.brightwheelexercise.ui.theme.BrightwheelExerciseTheme
import com.github.ephelsa.brightwheelexercise.utils.DataState

@Composable
fun FeedScreen(
    mainViewModel: MainViewModel
) {
    val onRepositories by mainViewModel.onRepositories.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        mainViewModel.fetchRepositoryInformation()
    }

    BrightwheelExerciseTheme {
        FeedTemplate(
            repositoriesInfoState = onRepositories,
            listState = listState,
            isLoading = onRepositories is DataState.UpdatingContent
        ) {
            mainViewModel.fetchNextRepositoryInformationContent()
        }
    }
}