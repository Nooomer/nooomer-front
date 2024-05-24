package com.nooomer.osu.components

import io.kvision.core.Container
import io.kvision.html.*

object HeaderComponent {

    fun Container.navBar(){
        nav(className = "navbar bg-success navbar-expand-lg") {
//            setStyle("background-color", "#8a2be2")
            div(className = "container-fluid") {
                link(
                    label = "Nooomer",
                    url = "/",
                    className = "navbar-brand"
                )
                button(className = "navbar-toggler", text = "") {
                    span(className = "navbar-toggler-icon")
                }
                div(className = "collapse navbar-collapse") {
                    this.id = "navbarSupportedContent"
                    ul(className = "navbar-nav me-auto mb-2 mb-lg-0") {
                        li(className = "nav-item") {
                            link(
                                label = "Leaderboard",
                                url = "/leaderboard",
                                className = "nav-link active"
                            )
                        }
                    }
                }
            }
        }
    }
}