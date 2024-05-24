package com.nooomer.osu.models.playerInfo

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val player: Player, val status: String,
)
