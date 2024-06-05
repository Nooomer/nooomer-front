package com.nooomer.osu.components

import com.nooomer.osu.components.HeaderComponent.navBar
import com.nooomer.osu.components.enums.LeaderboardsView
import com.nooomer.osu.components.states.AppState
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.onChange
import io.kvision.form.select.select
import io.kvision.html.ButtonStyle
import io.kvision.html.ButtonType
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.toast.ToastContainer
import io.kvision.toast.ToastContainerPosition
import kotlinx.browser.window

object SettingsComponent {
    private var modeList = LeaderboardsView.entries.map {
        StringPair(it.toString(), it.name.lowercase())
    }
    private var chooises = 0
    private var alreadyValueState = ObservableValue("")

    private fun String.toIndex(): Int {
        when (this) {
            "vanila" -> return 0
            "relax" -> return 1
            "autopilot" -> return 2
        }
        return -1
    }

    fun Container.settings(appState: ObservableValue<AppState>) {
        if (!window.localStorage.getItem("defaultLeaderboardType").isNullOrEmpty()) {
            alreadyValueState.value = window.localStorage.getItem("defaultLeaderboardType")?.lowercase().toString()
        }
        navBar()
        div(className = "settings d-flex align-items-center justify-content-center flex-column") {
            div(className = "settings d-flex flex-column justify-content-center").bind(alreadyValueState) {
                select(
                    value = alreadyValueState.value,
                    options = modeList,
                    label = "Default leaderboard type",
                ) {
                    this.selectedIndex = alreadyValueState.value.toIndex()
                }.onChange {
                    chooises = this.selectedIndex
                }
                button(className = "settings-save-button", text = "Save", type = ButtonType.SUBMIT).onClick {
                    when (chooises) {
                        0 -> {
                            console.log("chooises is $chooises")
                            window.localStorage.setItem("defaultLeaderboardType", LeaderboardsView.VANILA.toString())
                        }

                        1 -> {
                            console.log("chooises is $chooises")
                            window.localStorage.setItem("defaultLeaderboardType", LeaderboardsView.RELAX.toString())
                        }

                        2 -> {
                            console.log("chooises is $chooises")
                            window.localStorage.setItem("defaultLeaderboardType", LeaderboardsView.AUTOPILOT.toString())
                        }
                    }
                    ToastContainer(ToastContainerPosition.BOTTOMRIGHT).showToast(
                        message = "Default mode saved",
                        title ="Setting",
                        delay = 2000
                    )
                    this.style = ButtonStyle.SUCCESS
                    this.text = "Saved"
                }
            }

        }
    }

}