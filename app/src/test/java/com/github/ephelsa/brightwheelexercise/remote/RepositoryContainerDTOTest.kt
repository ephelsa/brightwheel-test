package com.github.ephelsa.brightwheelexercise.remote

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RepositoryContainerDTOTest {

    @Test
    fun asDomain() {
        // Given
        val repositoryContainerDTO = RepositoryContainerDTO(
            listOf(
                RepositoryContainerDTO.RepositoryDTO(
                    id = 1, fullName = "johndoe/repository", stars = 1_000
                ),
                RepositoryContainerDTO.RepositoryDTO(
                    id = 2, fullName = "pepito/top-repo", stars = 2_000
                )
            )
        )

        // When
        val got = repositoryContainerDTO.asModel()

        // Then
        got shouldHaveSize 2
        got.first().should {
            it.id shouldBe 1
            it.fullName shouldBe "johndoe/repository"
            it.stars shouldBe 1_000
            it.topContributor shouldBe null
        }

        got.last().should {
            it.id shouldBe 2
            it.fullName shouldBe "pepito/top-repo"
            it.stars shouldBe 2_000
            it.topContributor shouldBe null
        }
    }
}