package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.utils.ResultListOfRepositoryInformation

interface RepoInformationRepository {
    suspend fun fetchRepositoriesByPages(page: Int): ResultListOfRepositoryInformation
}