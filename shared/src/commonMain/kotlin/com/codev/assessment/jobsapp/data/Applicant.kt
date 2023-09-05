package com.codev.assessment.jobsapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Applicant(
    var id: String,
    var fullName: String,
    var emailAddress: String,
) {
    constructor() : this(
        "",
        "",
        ""
    )
}