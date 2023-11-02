@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package com.github.ephelsa.brightwheelexercise.di

import com.github.ephelsa.brightwheelexercise.repository.RepoInformationRepositoryImpl
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import org.junit.jupiter.api.Test

class DIModuleTest {

    @Test
    fun `provideRepoInformationRepository returns the right instance`() {
        // When
        val got = DIModule.provideRepoInformationRepository()

        // Then
        got should beInstanceOf<RepoInformationRepositoryImpl>()
    }
}