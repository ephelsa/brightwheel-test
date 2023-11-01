package com.github.ephelsa.brightwheelexercise.ui.template

import androidx.compose.runtime.Composable
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.ui.component.RepositoryCardList

@Composable
fun FeedTemplate(
    repositoriesInfo: Result<List<RepositoryInformation>>
) {
    repositoriesInfo.onSuccess { repoList ->
        RepositoryCardList(list = repoList)
    }
}
