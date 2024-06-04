package com.nooomer.osu.models.leaderboard

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
@ExperimentalSerializationApi
@Serializable
data class Leaderboard(
    @JsonNames("player_id")
    val playerId : Int,
    val name : String,
    val country : String,
    val tscore : Long,
    val rscore : Long,
    val pp : Int,
    val plays : Int,
    val playtime : Int,
    val acc : Double,
    @JsonNames("max_combo")
    val maxCombo : Int,
    @JsonNames("xh_count")
    val xhCount : Int,
    @JsonNames("x_count")
    val xCount : Int,
    @JsonNames("sh_count")
    val shCount : Int,
    @JsonNames("s_count")
    val sCount : Int,
    @JsonNames("a_count")
    val aCount : Int,
    @JsonNames("clan_id")
    val clanId : String?,
    @JsonNames("clan_name")
    val clanName : String?,
    @JsonNames("clan_tag")
    val clanTag : String?
)
