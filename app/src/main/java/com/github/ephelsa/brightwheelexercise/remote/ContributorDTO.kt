package com.github.ephelsa.brightwheelexercise.remote

import com.github.ephelsa.brightwheelexercise.domain.Contributor
import com.github.ephelsa.brightwheelexercise.domain.DomainMapper

data class ContributorDTO(
    val login: String
) : DomainMapper<Contributor> {
    override fun asDomain(): Contributor = Contributor(login)
}