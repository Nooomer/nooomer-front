package com.nooomer.osu.models.leaderboard

import kotlinx.serialization.Serializable

@Serializable
data class Response(val status : String,
                    val leaderboard : MutableList<Leaderboard>
)
