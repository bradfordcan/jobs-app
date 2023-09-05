package com.codev.assessment.jobsapp.remote.body

import kotlinx.serialization.Serializable

@Serializable
data class NewJobRequest(
    val noOfOpenings: Int,
    val title: String,
    val description: String,
    val industry: Int

    ) {
    constructor() : this(
        0,
        "",
        "",
        0
    ) // industry should be 0-8
}