package com.codev.assessment.jobsapp.remote.body

import kotlinx.serialization.Serializable

@Serializable
data class NewJobRequest(
    var noOfOpenings: Int,
    var title: String,
    var description: String,
    var industry: Int

    ) {
    constructor() : this(
        0,
        "",
        "",
        0
    ) // industry should be 0-8
}