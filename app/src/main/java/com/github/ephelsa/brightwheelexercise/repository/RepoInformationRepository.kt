package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation

interface RepoInformationRepository {
    suspend fun fetchRepositoriesByPages(page: Int): Result<List<RepositoryInformation>>
}