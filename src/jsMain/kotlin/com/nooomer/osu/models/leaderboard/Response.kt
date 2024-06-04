package com.nooomer.osu.models.leaderboard

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Response(val status : String,
                    val leaderboard : MutableList<Leaderboard>
)
