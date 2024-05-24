package com.nooomer.osu.models.v1

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Meta(
    val total: Int,
    val page: Int,
    @JsonNames("page_size")
    val pageSize: Int,
)
