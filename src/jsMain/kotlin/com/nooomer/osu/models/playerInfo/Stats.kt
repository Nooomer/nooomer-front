package com.nooomer.osu.models.playerInfo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Stats(
    val id: Int,
    val mode: Int,
    val tscore: Long,
    val rscore: Long,
    val pp: Int,
    val plays: Int,
    val playtime: Int,
    val acc: Double,
    @JsonNames("max_combo")
    val maxCombo: Int,
    @JsonNames("total_hits")
    val total_hits: Int,
    @JsonNames("replay_views")
    val replay_views: Int,
    @JsonNames("xh_count")
    val xh_count: Int,
    @JsonNames("x_count")
    val x_count: Int,
    @JsonNames("sh_count")
    val sh_count: Int,
    @JsonNames("s_count")
    val s_count: Int,
    @JsonNames("a_count")
    val a_count: Int,
    val rank: Int,
    @JsonNames("country_rank")
    val country_rank: Int,
)
