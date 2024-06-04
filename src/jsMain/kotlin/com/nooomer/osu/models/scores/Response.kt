package com.nooomer.osu.models.scores

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Response(
    var status: String,
    var scores: ArrayList<Scores>,
    var player: Player,
)