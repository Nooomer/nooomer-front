package com.nooomer.osu.models.v1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class Meta(
    val total: Int,
    val page: Int,
    @JsonNames("page_size")
    val pageSize: Int,
)
