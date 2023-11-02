package com.github.ephelsa.brightwheelexercise.repository

import com.github.ephelsa.brightwheelexercise.datasource.RemoteRepoInfoDatasource
import com.github.ephelsa.brightwheelexercise.domain.Contributor
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import retrofit2.HttpException

class RepoInformationRepositoryImpl(
    private val remoteDatasource: RemoteRepoInfoDatasource
) : RepoInformationRepository {

    override suspend fun fetchRepositoriesByPages(page: Int): Result<List<RepositoryInformation>> {
        return try {
            val response = remoteDatasource.fetchReposInfoByPage(page)

            val mergedContributor = response.map {
                val contributor = retrieveTopContributor(it.fullName)
                it.copy(topContributor = contributor)
            }

            Result.success(mergedContributor)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun retrieveTopContributor(fullName: String): Contributor? {
        return try {
            remoteDatasource.fetchTopRepositoryContributor(fullName)
        } catch (e: HttpException) {
            null
        }
    }
}