package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.datasource.RemoteRepoInfoDatasource
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation

class RepoInformationRepositoryImpl(
    private val remoteDatasource: RemoteRepoInfoDatasource
) : RepoInformationRepository {

    override suspend fun fetchRepositoriesByPages(page: Int): Result<List<RepositoryInformation>> {
        return try {
            val response = remoteDatasource.fetchReposInfoByPage(page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}