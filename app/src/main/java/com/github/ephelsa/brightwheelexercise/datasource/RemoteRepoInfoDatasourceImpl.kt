package com.github.ephelsa.brightwheelexercise.datasource

import com.github.ephelsa.brightwheelexercise.model.Contributor
import com.github.ephelsa.brightwheelexercise.model.RepositoryInformation
import com.github.ephelsa.brightwheelexercise.remote.ContributorDTO
import com.github.ephelsa.brightwheelexercise.remote.GithubRepositoryService
import com.github.ephelsa.brightwheelexercise.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteRepoInfoDatasourceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val ghRepositoryService: GithubRepositoryService
) : RemoteRepoInfoDatasource {

    override suspend fun fetchReposInfoByPage(page: Int): List<RepositoryInformation> =
        withContext(dispatcher) {
            val response = ghRepositoryService.starredRepository(
                page = page,
                contentPerPage = Constants.ContentPerPage
            )
            response.asModel()
        }

    override suspend fun fetchTopRepositoryContributor(fullName: String): Contributor =
        withContext(dispatcher) {
            val split = fullName.split("/")
            val response = ghRepositoryService.topContributor(
                username = split[0],
                repoName = split[1],
            )
            response.map(ContributorDTO::asModel).first()
        }
}