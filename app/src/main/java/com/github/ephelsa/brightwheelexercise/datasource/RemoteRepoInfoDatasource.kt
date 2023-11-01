package com.github.ephelsa.brightwheelexercise.datasource

import com.github.ephelsa.brightwheelexercise.domain.Contributor
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation

interface RemoteRepoInfoDatasource {
    suspend fun fetchReposInfoByPage(page: Int): List<RepositoryInformation>

    suspend fun fetchTopRepositoryContributor(fullName: String): Contributor
}