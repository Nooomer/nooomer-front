package com.nooomer.osu.interfaces

import io.kvision.rest.RestClient
import io.kvision.state.ObservableState
import kotlinx.coroutines.Job

interface Request {
    fun <T> makeRequest(restClient: RestClient, state: ObservableState<T>): Job
}