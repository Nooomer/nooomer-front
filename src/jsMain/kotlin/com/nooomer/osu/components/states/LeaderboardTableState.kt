package com.nooomer.osu.components.states

import com.nooomer.osu.models.leaderboard.Leaderboard

data class LeaderboardTableState(val lbUsers: MutableList<Leaderboard> = mutableListOf())
