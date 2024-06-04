package com.nooomer.osu.models.playerInfo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Response(
    val player: Player, val status: String,
)
