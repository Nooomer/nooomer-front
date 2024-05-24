package com.nooomer.osu.components

import com.nooomer.osu.components.HeaderComponent.navBar
import com.nooomer.osu.components.NavLinksComponent.modsNavLinks
import com.nooomer.osu.components.enums.ScoresOrigin
import com.nooomer.osu.components.states.AppState
import com.nooomer.osu.components.states.LeaderboardState
import com.nooomer.osu.components.states.LeaderboardTableState
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.link
import io.kvision.html.td
import io.kvision.html.tr
import io.kvision.pace.Pace
import io.kvision.rest.RestClient
import io.kvision.rest.call
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.table.table
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object LeaderboardsComponent {
    private var leaderboardState = ObservableValue(LeaderboardState())
    private var leaderboardTableState = ObservableValue(LeaderboardTableState())
    fun Container.leaderboards(restClient: RestClient, appState: ObservableValue<AppState>){
        navBar()
        div(className = "leaderboard").bind(leaderboardState) {
            modsNavLinks(restClient, appState, leaderboardState, ScoresOrigin.LEADERBOARD)
            div(className = "leaderboard_table").bind(leaderboardTableState) {
                if (leaderboardTableState.value.lbUsers.isNotEmpty()) {
                    Pace.stop()
                    table(listOf("Nickname", "PP","Accuracy", "Playtime"), tbodyDivider = true){
                        it.lbUsers.forEach {
                            tr {
                                td{
                                    link(label= it.name, url = "/u/${it.playerId}")
                                }
                                td(content = it.pp.toString())
                                td(content = it.acc.toString())
                                td(content = (it.playtime/3600).toString())
                            }
                        }
                    }
                }
            }
        }
    }

    fun makeLeaderboardRequest(restClient: RestClient, mode: String): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            console.info("Score state size: ${leaderboardTableState.value.lbUsers.size}")
            restClient.call<com.nooomer.osu.models.leaderboard.Response>("https://api.nooomer.ru/v1/get_leaderboard?mode=${mode}")
                .then {
                    console.log("Job completed, status: ${it.status}")
                   leaderboardTableState.value = leaderboardTableState.value.copy(lbUsers = it.leaderboard)
                    console.log("Added ${leaderboardTableState.value.lbUsers.size} users")
                }
                .catch {
                    console.log("Job failed, status: ${it.message}")
                }
        }
    }
}