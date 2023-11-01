package com.github.ephelsa.brightwheelexercise.datasource

import com.github.ephelsa.brightwheelexercise.domain.Contributor
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.remote.GithubRepositoryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteRepoInfoDatasourceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val ghRepositoryService: GithubRepositoryService
) : RemoteRepoInfoDatasource {

    override suspend fun fetchReposInfoByPage(page: Int): List<RepositoryInformation> =
        withContext(dispatcher) {
            // TODO: Move the constant
            val response = ghRepositoryService.starredRepository(page = page, contentPerPage = 15)

            response.asDomain()
        }

    override suspend fun fetchTopRepositoryContributor(fullName: String): Contributor =
        withContext(dispatcher) {
            TODO("Not yet implemented")
        }
}