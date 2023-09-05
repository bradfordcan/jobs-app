package com.codev.assessment.jobsapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Job(
    val id: String,
    val noOfOpenings: Int,
    val title: String,
    val description: String,
    val industry: Int,

    ) {
    constructor() : this(
        "100e5ae9-155f-4dd7-a2fe-09923a417fea",
        2,
        "Senior Angular Developer",
        "10+ years of experience in Angular",
        4
    )
}