package com.nooomer.osu.components

import com.nooomer.osu.components.NavLinksComponent.modsNavLinks
import com.nooomer.osu.components.ScoresComponent.scoreTable
import com.nooomer.osu.components.enums.LeaderboardsView
import com.nooomer.osu.components.enums.ScoreType
import com.nooomer.osu.components.enums.ScoresOrigin
import com.nooomer.osu.components.states.AppState
import com.nooomer.osu.components.states.LeaderboardState
import com.nooomer.osu.components.states.ScoresState
import com.nooomer.osu.interfaces.Request
import com.nooomer.osu.models.playerInfo.Player
import com.nooomer.osu.models.playerInfo.Response
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.pace.Pace
import io.kvision.rest.RestClient
import io.kvision.rest.call
import io.kvision.state.ObservableState
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.utils.px
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.js.Date
import kotlin.math.abs

@ExperimentalSerializationApi
object ProfileComponent : Request {

    private var player: Player? = null
    private var headerState = ObservableValue(player)
    private var leaderboardState = ObservableValue(LeaderboardState())
    private var scoresState = ObservableValue(ScoresState())
    private var startFlag = false
    private var job: Job? = null
    fun Container.profile(restClient: RestClient, appState: ObservableValue<AppState>) {
        Pace.start()
        header(className = "d-flex bd-highlight mb-3 align-items-center bg-success bg-gradient") {
            div(className = "col col-1") {
                image(
                    "https://a.${window.location.host}/${appState.value.content}", // TODO: поменять nooomer на window.location.host
                    className = "user-avatar img-fluid float-start"
                )
            }
            div(className = "p-2 bd-highlight").bind(headerState) {
                if (it == null && !startFlag) {
                    job = makeRequest(restClient, appState)
                    console.log("Started $job")
                    startFlag = true
                } else {
                    console.log("Complete job: $job")
                    job = null
                    div(className = "row align-items-center") {
                        this.paddingRight = 0.px
                        this.paddingLeft = 10.px
                        h1("${player?.info?.name}") {
                            this.paddingLeft = 5.px
                        }
                        div(
                            content = "VN-PP: ${player?.listOfStats?.vannila?.pp}"
                        ) {
                            this.width = 120.px
                            this.paddingRight = 0.px
                            this.paddingLeft = 5.px
                        }
                        div(
                            content = "RX-PP: ${player?.listOfStats?.relax?.pp}"
                        )
                        {
                            this.width = 120.px
                            this.paddingRight = 0.px
                            this.paddingLeft = 5.px
                        }
                        div(
                            content = "AP-PP: ${player?.listOfStats?.autopilot?.pp}"
                        )
                        {
                            this.width = 120.px
                            this.paddingRight = 0.px
                            this.paddingLeft = 5.px
                        }
                    }
                    div(
                        content = io.kvision.i18n.tr("Last activity: ")
                                +
                                getDateTime(player?.info?.latestActivity!!.toLong())
                    ) {
                        setStyle("width", "fit-content")
                        enableTooltip(
                            TooltipOptions(
                                title = getTooltipForTime(player?.info?.latestActivity!!.toLong()).ifEmpty {
                                    "Can't track online time :("
                                },
                                rich = true,
                                placement = Placement.TOP,
                            )
                        )
                    }
                }
            }
            div(className = "ms-auto p-2 bd-highlight") {
                link("Leaderboard", "/leaderboard", className = "link-dark")
            }
        }
        if(!window.localStorage.getItem("defaultLeaderboardType").isNullOrEmpty()){
            leaderboardState.value = leaderboardState.value.copy(view = LeaderboardsView.valueOf(window.localStorage.getItem("defaultLeaderboardType")!!))
        }
        div(className = "scores").bind(leaderboardState) {
            if(appState.value.content == "5"){
                background = Background(image = "img/ona_bg.png")
                addCssStyle(Style(
                    ".table",
                ))
                setStyle("background-size", "cover")
                setStyle("background-repeat", "no-repeat")
                setStyle("background-position", "center center")
            }
            scoresState.value = ScoresState()
            modsNavLinks(restClient, appState, leaderboardState, ScoresOrigin.USER)
            div(className = "scores_table").bind(scoresState) {
                if (scoresState.value.bestScores.isNotEmpty() and scoresState.value.recentScores.isNotEmpty()) {
                    h1(content = "${ScoreType.BEST.name.lowercase().replaceFirst(ScoreType.BEST.name.lowercase()[0], ScoreType.BEST.name[0])} ${leaderboardState.value.view.name.lowercase()} scores")
                    scoreTable(it.bestScores, ScoreType.BEST, leaderboardState.value.view.name.lowercase(), appState)
                    h1(content = "${ScoreType.RECENT.name.lowercase().replaceFirst(ScoreType.RECENT.name.lowercase()[0], ScoreType.RECENT.name[0])} ${leaderboardState.value.view.name.lowercase()} scores")
                    scoreTable(it.recentScores, ScoreType.RECENT, leaderboardState.value.view.name.lowercase(), appState)
                    Pace.stop()
                }
            }
        }
    }

    fun <T> makePlayerScoresRequest(restClient: RestClient, state: ObservableState<T>, scope: String, mode: String): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            restClient.call<com.nooomer.osu.models.scores.Response>("https://api.nooomer.ru/v1/get_player_scores?scope=${scope}&id=${(state.getState() as AppState).content}&mode=${mode}&limit=10")
                .then {
                    console.log("Job completed, status: ${it.status}")
                    when (scope) {
                        "best" -> {
                            scoresState.value = scoresState.value.copy(bestScores = it.scores)
                            console.log("Getted ${scoresState.value.bestScores.size} ${scope}'s scores for $mode mode")
                        }

                        "recent" -> {
                            scoresState.value = scoresState.value.copy(recentScores = it.scores)
                            console.log("Getted ${scoresState.value.recentScores.size} ${scope}'s scores for $mode mode")
                        }
                    }
                }
                .catch {
                    console.log("Job failed, status: ${it.message}")
                }
        }
    }

    override fun <T> makeRequest(restClient: RestClient, state: ObservableState<T>): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            console.info("Id: ${(state.getState() as AppState).content}")
            restClient.call<Response>("https://api.nooomer.ru/v1/get_player_info?scope=all&id=${(state.getState() as AppState).content}")
                .then {
                    console.log("Job completed, status: ${it.status}")
                    player = it.player
                    headerState.value = it.player
                    console.log("Getted info about: ${it.player.info.name}")
                }
                .catch {
                    console.log("Job failed, status: ${it.message}")
                }
        }
    }

    private fun getTooltipForTime(timestamp: Long): String {
        val currDate = Date(Date.now())
        val d = Date(timestamp * 1000)
        console.log("Current day: ${currDate.getDate()}, user day:${d.getDate()}")
        if ((d.getDate() - currDate.getDate()) == 0) {
            return io.kvision.i18n.tr("Today")
        }
        if (abs(d.getDate() - currDate.getDate()) == 1) {
            return io.kvision.i18n.tr("Yesterday")
        }
        if (abs(d.getDate() - currDate.getDate()) == 2) {
            return io.kvision.i18n.tr("A day before yesterday")
        }
        if (abs(d.getDate() - currDate.getDate()) in 3..6) {
            return io.kvision.i18n.tr("A few days ago")
        }
        if (abs(d.getDate() - currDate.getDate()) == 7) {
            return io.kvision.i18n.tr("A week ago")
        }
        if ((abs(d.getDate() - currDate.getDate()) in 8..29)) {
            return io.kvision.i18n.tr("More than a week ago")
        }
        if (abs(d.getDate() - currDate.getDate()) == 30) {
            return io.kvision.i18n.tr("A month ago")
        }
        if (abs(d.getDate() - currDate.getDate()) > 30) {
            return io.kvision.i18n.tr("More than a month ago")
        }
        return String()
    }

    private fun getDateTime(timestamp: Long): String {
        val d = Date(timestamp * 1000)
        val year = d.getFullYear()
        val month = (d.getMonth() + 1)
        val day = d.getDate()
        val hour = d.getHours()
        val minutes = d.getMinutes()
        // ie: 2014-03-24, 3:00 PM
        return if (month < 10) {
            if (day < 10) {
                "$year-0$month-0$day, $hour:$minutes"
            } else {
                "$year-0$month-$day, $hour:$minutes"
            }
        } else {
            if (day < 10) {
                "$year-$month-0$day, $hour:$minutes"
            } else {
                "$year-$month-$day, $hour:$minutes"
            }
        }
    }
}
