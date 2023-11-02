package com.github.ephelsa.brightwheelexercise.remote

import com.github.ephelsa.brightwheelexercise.model.ModelMapper
import com.github.ephelsa.brightwheelexercise.model.RepositoryInformation
import com.google.gson.annotations.SerializedName

data class RepositoryContainerDTO(
    val items: List<RepositoryDTO>,
) : ModelMapper<List<RepositoryInformation>> {

    data class RepositoryDTO(
        val id: Long,
        @SerializedName("full_name") val fullName: String,
        @SerializedName("stargazers_count") val stars: Long,
    ) : ModelMapper<RepositoryInformation> {

        override fun asModel(): RepositoryInformation = RepositoryInformation(
            id = id,
            fullName = fullName,
            stars = stars,
            topContributor = null,
        )
    }

    override fun asModel(): List<RepositoryInformation> = items.map(RepositoryDTO::asModel)
}
