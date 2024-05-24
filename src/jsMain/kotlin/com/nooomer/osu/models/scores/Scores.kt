package com.nooomer.osu.models.scores

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Scores(

    var id: Int,
    var score: Int,
    var pp: Double,
    var acc: Double,
    @JsonNames("max_combo") var maxCombo: Int,
    var mods: Int,
    var n300: Int,
    var n100: Int,
    var n50: Int,
    var nmiss: Int,
    var ngeki: Int,
    var nkatu: Int,
    var grade: String,
    var status: Int,
    var mode: Int,
    @JsonNames("play_time") var playTime: String,
    @JsonNames("time_elapsed") var timeElapsed: Int,
    var perfect: Int,
    var beatmap: Beatmap,

    )