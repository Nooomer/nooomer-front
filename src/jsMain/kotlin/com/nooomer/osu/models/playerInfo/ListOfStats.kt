package com.nooomer.osu.models.playerInfo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class ListOfStats(
    @JsonNames("0")
val vannila : Stats,
    @JsonNames("4")
val relax : Stats,
    @JsonNames("8")
val autopilot : Stats,
)
