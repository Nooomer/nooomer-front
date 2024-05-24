package com.nooomer.osu.models.scores

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    var id: Int,
    var name: String,
    var clan: String?,
    )