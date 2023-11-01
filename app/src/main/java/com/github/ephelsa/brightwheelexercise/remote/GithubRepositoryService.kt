package com.github.ephelsa.brightwheelexercise.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubRepositoryService {

    @GET("search/repositories?q=stars:>0&sort=stars")
    suspend fun starredRepository(
        @Query("page") page: Int,
        @Query("per_page") contentPerPage: Int
    ): RepositoryContainerDTO

    @GET("repos/{fullName}/contributors?per_page=1")
    suspend fun topContributor(
        @Path("fullName") fullName: String
    )
}