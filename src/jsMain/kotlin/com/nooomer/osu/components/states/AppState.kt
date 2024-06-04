package com.nooomer.osu.components.states

import com.nooomer.osu.components.enums.AppView

data class AppState(
    val appView: AppView = AppView.START,
    val content: String = "0",
    val jobState: Boolean = false)
