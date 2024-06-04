package com.nooomer.osu.components

import com.nooomer.osu.components.HeaderComponent.navBar
import com.nooomer.osu.components.enums.LeaderboardsView
import com.nooomer.osu.components.states.AppState
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.onChange
import io.kvision.form.select.select
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.state.ObservableValue
import kotlinx.browser.window

object SettingsComponent {
    private var modeList = LeaderboardsView.entries.map {
            StringPair(it.toString(), it.name.lowercase())
        }
    private var chooises = 0
    fun Container.settings(appState: ObservableValue<AppState>) {
        if(!window.localStorage.getItem("defaultLeaderboardType").isNullOrEmpty()){

        }
        navBar()
        div(className = "settings d-flex") {
            div(className = "settings d-flex justify-content-center"){
                select(
                    modeList,
                    label = "Default leaderboard type",
                ).onChange{
                    chooises = selectedIndex
                }
                button(className = "settings d-float-right", text = "Save").onClick {
                    when(chooises){
                        0 ->{
                            console.log("chooises is $chooises")
                            window.localStorage.setItem("defaultLeaderboardType", LeaderboardsView.VANILA.toString())
                        }
                        1 ->{
                            console.log("chooises is $chooises")
                            window.localStorage.setItem("defaultLeaderboardType", LeaderboardsView.RELAX.toString())
                        }
                        2 ->{
                            console.log("chooises is $chooises")
                            window.localStorage.setItem("defaultLeaderboardType", LeaderboardsView.AUTOPILOT.toString())
                        }
                    }
                }
            }

        }
    }

}