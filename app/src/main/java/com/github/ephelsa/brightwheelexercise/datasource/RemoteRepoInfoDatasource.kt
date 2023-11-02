package com.github.ephelsa.brightwheelexercise.datasource

import com.github.ephelsa.brightwheelexercise.model.Contributor
import com.github.ephelsa.brightwheelexercise.model.RepositoryInformation

interface RemoteRepoInfoDatasource {
    suspend fun fetchReposInfoByPage(page: Int): List<RepositoryInformation>

    suspend fun fetchTopRepositoryContributor(fullName: String): Contributor
}