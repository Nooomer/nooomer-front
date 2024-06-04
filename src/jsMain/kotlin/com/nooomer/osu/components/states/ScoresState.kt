package com.nooomer.osu.components.states

import com.nooomer.osu.models.scores.Scores
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
data class ScoresState(val bestScores: MutableList<Scores> = mutableListOf(), val recentScores: MutableList<Scores> = mutableListOf(), val scores: MutableList<Scores> = mutableListOf())
