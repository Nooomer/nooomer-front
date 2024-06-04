package com.nooomer.osu.components.states

import com.nooomer.osu.models.leaderboard.Leaderboard
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
data class LeaderboardTableState(val lbUsers: MutableList<Leaderboard> = mutableListOf())
