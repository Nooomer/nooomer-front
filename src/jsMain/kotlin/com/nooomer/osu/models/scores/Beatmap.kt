package com.nooomer.osu.models.scores

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class Beatmap(

    var md5: String,
    var id: Int,
    @JsonNames("set_id") var setId: Int,
    var artist: String,
    var title: String,
    var version: String,
    var creator: String,
    @JsonNames("last_update") var lastUpdate: String? = null,
    @JsonNames("total_length") var totalLength: Int,
    @JsonNames("max_combo") var maxCombo: Int,
    var status: Int,
    var plays: Int? = null,
    var passes: Int? = null,
    var mode: Int,
    var bpm: Double,
    var cs: Double,
    var od: Double,
    var ar: Double,
    var hp: Double,
    var diff: Double,
)