package com.nooomer.osu

import com.nooomer.osu.components.HomeComponent.home
import com.nooomer.osu.components.LeaderboardsComponent.leaderboards
import com.nooomer.osu.components.ProfileComponent.profile
import com.nooomer.osu.components.enums.AppView
import com.nooomer.osu.components.states.AppState
import com.nooomer.osu.models.playerInfo.Response
import io.kvision.*
import io.kvision.html.Link
import io.kvision.html.h1
import io.kvision.html.header
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.pace.Pace
import io.kvision.panel.root
import io.kvision.rest.RestClient
import io.kvision.rest.call
import io.kvision.routing.Routing
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class App : Application() {
    var userExist = false
    override fun start() {
        val routing = Routing("/", useHash = false)
        val appState = ObservableValue(AppState())
        var restClient = RestClient()
        Link.useDataNavigoForLinks = true
        Pace.init(require("css/loading-bar.css"))
        I18n.manager =
            DefaultI18nManager(
                mapOf(
                    "pl" to require("i18n/messages-pl.json"),
                    "en" to require("i18n/messages-en.json"),
                    "ru" to require("i18n/messages-ru.json")
                )
            )

        root("kvapp").bind(appState) { currentState ->
            when (currentState.appView) {
                AppView.MAIN -> {
                    home(restClient)
                }

                AppView.PROFILE -> {
                    var job: Job? = null
                    if (!appState.value.jobState) {
                        job = checkUserExist(appState.value.content.toInt(), restClient, appState)
                    }
                    if (appState.value.content.toInt() == 1) {
                        appState.value = appState.value.copy(appView = AppView.NOTFOUND, content = "")
                    } else {
                        if (appState.value.jobState) {
                            if (userExist) {
                                console.log("User exist, field: $userExist, job: $job")
                                profile(restClient, appState)
                            } else {
                                console.warn("User not exist, field: $userExist")
                                appState.value = appState.value.copy(appView = AppView.NOTFOUND, content = "")
                            }
                        }
                    }
                }

                AppView.LEADERBOARD -> {
                    leaderboards(restClient, appState)
                }

                AppView.NOTFOUND -> {
                    header(className = "container-xs row align-items-center bg-success bg-gradient")
                    h1("Not found")
                    routing.updatePageLinks()
                }

                AppView.START -> {

                }
            }
        }
        window.setTimeout({ routing.updatePageLinks() }, 0)
        routing
            .on("/", {
                appState.value = appState.value.copy(appView = AppView.MAIN)
                routing.updatePageLinks()
                console.log("State: main, state: ${appState.value}")
            })
            .on("u/:id", { match ->
                val id = match.data["id"].toString()
                appState.value = appState.value.copy(appView = AppView.PROFILE, content = id)
                routing.updatePageLinks()
                console.log("State: profile, state: ${appState.value}")
            })
            .on("/leaderboard", {
                appState.value = appState.value.copy(appView = AppView.LEADERBOARD)
                routing.updatePageLinks()
                console.log("State: leaderboard, state: ${appState.value}")
            })
            .notFound({
                appState.value = appState.value.copy(appView = AppView.NOTFOUND, content = "")
                routing.updatePageLinks()
                console.log("State: not found, state: ${appState.value}")
            })
            .resolve()
    }

    fun checkUserExist(id: Int, restClient: RestClient, appState: ObservableValue<AppState>): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            restClient.call<Response>("https://api.nooomer.ru/v1/get_player_info?scope=all&id=$id")
                .then {
                    console.log("Job completed, status: ${it.status}")
                    userExist = true
                    appState.value = appState.value.copy(jobState = true)
                    console.log("New state: ${appState.value}, user exist: $userExist")
                }
                .catch {
                    console.log("Job failed, status: ${it.message}")
                    userExist = false
                    appState.value = appState.value.copy(jobState = true)
                    console.log("New state: ${appState.value}, user exist: $userExist")
                }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        DatetimeModule,
        RichTextModule,
        BootstrapUploadModule,
        BootstrapIconsModule,
        ChartModule,
        CoreModule
    )
}
