package com.github.ephelsa.brightwheelexercise.remote

import com.github.ephelsa.brightwheelexercise.domain.DomainMapper
import com.github.ephelsa.brightwheelexercise.domain.RepositoryInformation
import com.google.gson.annotations.SerializedName

data class RepositoryContainerDTO(
    val items: List<RepositoryDTO>,
) : DomainMapper<List<RepositoryInformation>> {

    data class RepositoryDTO(
        val id: Long,
        @SerializedName("full_name") val fullName: String,
        @SerializedName("stargazers_count") val stars: Long,
    ) : DomainMapper<RepositoryInformation> {

        override fun asDomain(): RepositoryInformation = RepositoryInformation(
            id = id,
            fullName = fullName,
            stars = stars,
            topContributor = null,
        )
    }

    override fun asDomain(): List<RepositoryInformation> = items.map(RepositoryDTO::asDomain)
}
