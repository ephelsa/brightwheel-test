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

    var onRepositories = MutableStateFlow<Result<List<RepositoryInformation>>?>(null)
        private set

    fun fetchRepositoryInformation() = viewModelScope.launch {
        onRepositories.value = repoInformationRepository.fetchRepositoriesByPages(2)
    }
}