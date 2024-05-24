package com.nooomer.osu.components

import com.nooomer.osu.components.HeaderComponent.navBar
import com.nooomer.osu.interfaces.Request
import com.nooomer.osu.models.v1.PlayerInfo
import com.nooomer.osu.models.v1.Response
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.li
import io.kvision.html.link
import io.kvision.html.ul
import io.kvision.modal.Alert
import io.kvision.pace.Pace
import io.kvision.rest.RestClient
import io.kvision.rest.call
import io.kvision.state.ObservableListWrapper
import io.kvision.state.ObservableState
import io.kvision.state.bind
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private var playerInfos: MutableList<PlayerInfo> = arrayListOf()
private val playerObserver = ObservableListWrapper(playerInfos)

private var startFlag = false

object HomeComponent : Request {


    override fun <T> makeRequest(restClient: RestClient, state: ObservableState<T>): Job {
        var convState = state.getState() as MutableList<PlayerInfo>
        val job = CoroutineScope(Dispatchers.Default).launch {
            restClient.call<Response>("https://api.nooomer.ru/v2/players")
                .then {
                    console.log("Job completed, players count: ${playerInfos.size}")
                    convState.addAll(it.data)
                    console.log("Players added to obs, players in obs: ${convState.size}")
                }
                .catch {
                    Alert(text = "${it.message}")
                }


        }
        console.log("Job $job started")
        return job
    }

    fun Container.home(restClient: RestClient) {
//        header(className = "d-flex bd-highlight mb-3 align-items-center bg-success bg-gradient") {
//            div(className = "p-2 bd-highlight") {
//                h1(io.kvision.i18n.tr("Players"))
//            }
//            div(className = "ms-auto p-2 bd-highlight") {
//                link("Leaderboard", "/leaderboard")
//            }
//        }
        navBar()
        div(className = "scores").bind(playerObserver) { observer ->
            if (observer.isEmpty() and !startFlag) {
                Pace.start()
                var job = makeRequest(restClient, playerObserver)
                startFlag = true
            } else {
                console.log("player getting complete: ${observer.size}")
                Pace.stop()
                ul(className = "list-group") {
                    observer.forEach {
                        if ((it.id == 1) or (it.id == 2) or (it.id == 4) or (it.id == 10)) {
                            if (it.id == 1) {
                                li(className = "list-group-item disabled", content = it.name) {
                                    setAttribute("aria-disabled", "true")
                                }
                            }
                        } else {
                            li(className = "list-group-item") {
                                link(it.name, "/u/${it.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}