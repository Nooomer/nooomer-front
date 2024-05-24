package com.nooomer.osu.models.v1

import kotlinx.serialization.Serializable

@Serializable
data class Response(val status : String,
                    val data : MutableList<PlayerInfo>,
                    val meta : Meta
)
