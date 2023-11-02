package com.github.ephelsa.brightwheelexercise.ui.activity

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepository
import com.github.ephelsa.brightwheelexercise.utils.DataState
import com.github.ephelsa.brightwheelexercise.utils.ResultListOfRepositoryInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
const val INITIAL_PAGE = 1

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
const val INITIAL_NEXT_PAGE = 2

class MainViewModel(
    private val repoInformationRepository: RepoInformationRepository,
) : ViewModel() {

    private var nextPage = INITIAL_NEXT_PAGE
    var onRepositories =
        MutableStateFlow<DataState<ResultListOfRepositoryInformation>>(DataState.Initialized())
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        set

    fun fetchRepositoryInformation() = viewModelScope.launch {
        onRepositories.value = DataState.Loading()
        val result = repoInformationRepository.fetchRepositoriesByPages(INITIAL_PAGE)
        onRepositories.value = DataState.ContentReady(result)
    }

    fun fetchNextRepositoryInformationContent() = viewModelScope.launch {
        onRepositories.value = DataState.UpdatingContent(onRepositories.value.contentReady)
        val result = repoInformationRepository.fetchRepositoriesByPages(nextPage)
        onRepositories.value = DataState.ContentReady(mergeIncomingRepositories(result))

        nextPage++
    }

    private fun mergeIncomingRepositories(
        incomingRepos: ResultListOfRepositoryInformation
    ): ResultListOfRepositoryInformation {
        val currentData = onRepositories.value.contentReady

        if (incomingRepos.isFailure) {
            return incomingRepos
        }

        val content =
            (currentData?.getOrDefault(listOf()) ?: listOf()) + incomingRepos.getOrDefault(
                listOf()
            )

        return Result.success(content)
    }
}
