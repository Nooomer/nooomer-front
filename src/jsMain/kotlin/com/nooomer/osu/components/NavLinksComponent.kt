package com.nooomer.osu.components

import com.nooomer.osu.components.LeaderboardsComponent.makeLeaderboardRequest
import com.nooomer.osu.components.ProfileComponent.makePlayerScoresRequest
import com.nooomer.osu.components.enums.LeaderboardsView
import com.nooomer.osu.components.enums.ScoresOrigin
import com.nooomer.osu.components.states.AppState
import com.nooomer.osu.components.states.LeaderboardState
import io.kvision.core.Container
import io.kvision.html.link
import io.kvision.html.nav
import io.kvision.pace.Pace
import io.kvision.rest.RestClient
import io.kvision.state.ObservableValue

object NavLinksComponent {
    fun Container.modsNavLinks(
        restClient: RestClient,
        appState: ObservableValue<AppState>,
        leaderboardState: ObservableValue<LeaderboardState>,
        scoresOrigin: ScoresOrigin
    ) {
        nav(className = "nav nav-pills justify-content-center") {
            when (leaderboardState.value.view) {
                LeaderboardsView.RELAX -> {
                    Pace.start()
                    link(className = "nav-link vanila", label = "Vanila").onClick {
                        leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.VANILA)
                    }
                    link(className = "nav-link active relax", label = "Relax")
                    link(className = "nav-link autopilot", label = "Autopilot").onClick {
                        leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.AUTOPILOT)
                    }
                    when(scoresOrigin){
                        ScoresOrigin.USER -> {
                            makePlayerScoresRequest(restClient, appState, "best", "4")
                            makePlayerScoresRequest(restClient, appState, "recent", "4")
                        }
                        ScoresOrigin.LEADERBOARD -> {
                            makeLeaderboardRequest(restClient, "4")
                        }
                    }
                }

                LeaderboardsView.AUTOPILOT -> {
                    Pace.start()
                    link(className = "nav-link vanila", label = "Vanila").onClick {
                        leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.VANILA)
                    }
                    link(className = "nav-link relax", label = "Relax").onClick {
                        leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.RELAX)
                    }
                    link(className = "nav-link active autopilot", label = "Autopilot")
                    when(scoresOrigin){
                        ScoresOrigin.USER -> {
                            makePlayerScoresRequest(restClient, appState, "best", "8")
                            makePlayerScoresRequest(restClient, appState, "recent", "8")
                        }
                        ScoresOrigin.LEADERBOARD -> {
                            makeLeaderboardRequest(restClient, "8")
                        }
                    }
                }

                LeaderboardsView.VANILA -> {
                    Pace.start()
                    link(className = "nav-link active vanila", label = "Vanila")
                    link(className = "nav-link relax", label = "Relax").onClick {
                        leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.RELAX)
                    }
                    link(className = "nav-link autopilot", label = "Autopilot").onClick {
                        leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.AUTOPILOT)
                    }
                    when(scoresOrigin){
                        ScoresOrigin.USER -> {
                            makePlayerScoresRequest(restClient, appState, "best", "0")
                            makePlayerScoresRequest(restClient, appState, "recent", "0")
                        }
                        ScoresOrigin.LEADERBOARD -> {
                            makeLeaderboardRequest(restClient, "0")
                        }
                    }
                }
            }
        }
    }
}