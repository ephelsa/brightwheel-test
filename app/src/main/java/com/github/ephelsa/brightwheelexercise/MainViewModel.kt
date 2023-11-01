package com.github.ephelsa.brightwheelexercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepository
import com.github.ephelsa.brightwheelexercise.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val INITIAL_PAGE = 1
private const val INITIAL_NEXT_PAGE = 2

class MainViewModel(
    private val repoInformationRepository: RepoInformationRepository,
) : ViewModel() {

    private var nextPage = INITIAL_NEXT_PAGE
    var onRepositories =
        MutableStateFlow<DataState<Result<List<RepositoryInformation>>>>(DataState.Initialized())
        private set

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

    private fun mergeIncomingRepositories(incomingRepos: Result<List<RepositoryInformation>>): Result<List<RepositoryInformation>> {
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
