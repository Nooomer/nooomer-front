package com.nooomer.osu.models.scores

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    var status: String,
    var scores: ArrayList<Scores>,
    var player: Player,
)