package com.github.ephelsa.brightwheelexercise.ui.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.ephelsa.brightwheelexercise.R
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.ui.component.RepositoryCardList
import com.github.ephelsa.brightwheelexercise.ui.component.UnexpectedContent
import com.github.ephelsa.brightwheelexercise.ui.theme.Space
import com.github.ephelsa.brightwheelexercise.utils.DataState
import com.github.ephelsa.brightwheelexercise.utils.ScopedCallback
import kotlinx.coroutines.CoroutineScope

@Composable
fun FeedTemplate(
    repositoriesInfoState: DataState<Result<List<RepositoryInformation>>>,
    listState: LazyListState,
    isLoading: Boolean,
    onLimitReached: ScopedCallback<CoroutineScope>,
) {
    when (repositoriesInfoState) {
        is DataState.ContentReady, is DataState.UpdatingContent -> FeedTemplateContentReady(
            repositoriesInfoState
        ) {
            RepositoryCardList(
                list = it,
                listState = listState,
                onLimitReached = onLimitReached,
                isLoading = isLoading
            )
        }

        else -> FeedTemplateLoading()
    }
}

@Composable
fun FeedTemplateContentReady(
    repositoriesInfoState: DataState<Result<List<RepositoryInformation>>>,
    onSuccessComponent: @Composable (List<RepositoryInformation>) -> Unit,
) {
    repositoriesInfoState.contentReady!!.onSuccess {
        if (it.isEmpty()) {
            UnexpectedContent(
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(60.dp)
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.error_emptyRepos),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        } else {
            onSuccessComponent(it)
        }
    }.onFailure {
        UnexpectedContent(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(60.dp)
                )
            },
        ) {
            Text(
                text = stringResource(id = R.string.error_unexpected),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = it.message.toString(),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
            )
        }
    }
}

@Composable
fun FeedTemplateLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.loading_fetchingRepositories),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = Space.Large)
        )
        LinearProgressIndicator()
    }
}