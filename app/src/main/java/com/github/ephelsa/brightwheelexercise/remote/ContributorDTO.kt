package com.github.ephelsa.brightwheelexercise.remote

import com.github.ephelsa.brightwheelexercise.model.Contributor
import com.github.ephelsa.brightwheelexercise.model.ModelMapper

data class ContributorDTO(
    val login: String
) : ModelMapper<Contributor> {
    override fun asModel(): Contributor = Contributor(login)
}