package com.codev.assessment.jobsapp.remote.body

import kotlinx.serialization.Serializable

@Serializable
data class ApplicantRequest(
    var fullName: String,
    var emailAddress: String,

    ) {
    constructor() : this(
        "",
        ""
    )
}