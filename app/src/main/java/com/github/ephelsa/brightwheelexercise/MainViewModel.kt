package com.github.ephelsa.brightwheelexercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repoInformationRepository: RepoInformationRepository,
) : ViewModel() {

    var onRepositories =
        MutableStateFlow<DataState<Result<List<RepositoryInformation>>>>(DataState.Initialized())
        private set

    fun fetchRepositoryInformation() = viewModelScope.launch {
        onRepositories.value = DataState.Loading()
        val result = repoInformationRepository.fetchRepositoriesByPages(2)
        onRepositories.value = DataState.ContentReady(result)
    }
}

sealed class DataState<T>(
    val contentReady: T? = null,
    val exception: Exception? = null,
    val errorMessage: String? = null,
) {
    class Initialized<T> : DataState<T>()
    class Loading<T> : DataState<T>()
    data class ContentReady<T>(val content: T) : DataState<T>(contentReady = content)
}