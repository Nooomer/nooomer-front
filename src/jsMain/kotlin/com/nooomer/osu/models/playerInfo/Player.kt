package com.nooomer.osu.models.playerInfo

import com.nooomer.osu.models.v1.PlayerInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class Player(val info : PlayerInfo,
                  @JsonNames("stats")
                  val listOfStats : ListOfStats
)
