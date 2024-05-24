package com.nooomer.osu.components.states

import com.nooomer.osu.models.scores.Scores

data class ScoresState(val bestScores: MutableList<Scores> = mutableListOf(), val recentScores: MutableList<Scores> = mutableListOf(), val scores: MutableList<Scores> = mutableListOf())
