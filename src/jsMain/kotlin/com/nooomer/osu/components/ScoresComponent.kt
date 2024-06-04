package com.nooomer.osu.components

import com.nooomer.osu.components.enums.ScoreType
import com.nooomer.osu.components.states.AppState
import com.nooomer.osu.models.scores.Scores
import com.nooomer.osu.utils.ModsConverter
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.link
import io.kvision.html.td
import io.kvision.html.tr
import io.kvision.state.ObservableValue
import io.kvision.table.TableType
import io.kvision.table.table
import kotlinx.browser.window
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object ScoresComponent {
    fun Container.scoreTable(scores: MutableList<Scores>, scoreType: ScoreType, mode: String, appState: ObservableValue<AppState>) {
        div(className = "${scoreType.name.lowercase()}-scores container table-responsive") {
            val classNames = if(appState.value.content == "5"){
                "table ona-table"
            } else{
                "table"
            }
            table(
                className = classNames,
                headerNames = listOf(
                    "Title",
                    "Version",
                    "PP",
                    "Accuracy",
                    "Max Combo",
                    "Misscount",
                    "Mods",
                    "Replay"
                ), tbodyDivider = true, types = mutableSetOf(TableType.HOVER)
            ) {
//                background-size: cover;
//                background-repeat: no-repeat;
//                background-position: center center;
                //setStyle("background", "rgba(255,255,255,0.3) !important")

                scores.forEachIndexed { index, score ->
                    tr {
                        td {
                            link(
                                label = score.beatmap.title,
                                url = "https://osu.${window.location.host}/beatmaps/${score.beatmap.id}"
                            )
                        }
                        td(
                            score.beatmap.version
                        )
                        td(
                            score.pp.toString()
                        )
                        td(
                            score.acc.toString()
                        )
                        td(
                            score.maxCombo.toString()
                        )
                        td(
                            score.nmiss.toString()
                        )
                        td(
                            ModsConverter.convert(score.mods)
                        ) {
                            if (index == scores.size-1) {
                               ModsConverter.endTimeMEasure()
                            }
                        }
                        td {
                            link(label = "Download", url = "/r?id=${score.id}")
                        }
                        td(

                        )
                    }
                }
            }
        }
    }
}