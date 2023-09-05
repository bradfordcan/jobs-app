package com.codev.assessment.jobsapp.repository

import io.ktor.client.engine.android.*
import org.koin.dsl.module



actual fun platformModule() = module {
    /*single {
        val driver =
            AndroidSqliteDriver(PeopleInSpaceDatabase.Schema, get(), "peopleinspace.db")

        PeopleInSpaceDatabaseWrapper(PeopleInSpaceDatabase(driver))
    }*/
    single { Android.create() }
}
