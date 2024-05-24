package com.nooomer.osu.models.v1

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class PlayerInfo(
    val id: Int,
    val name: String,
    @JsonNames("safe_name")
    val safeName: String,
    val priv: Int,
    val country: String,
    @JsonNames("silence_end")
    val silenceEnd: Int?,
    @JsonNames("donor_end")
    val donorEnd: Int?,
    @JsonNames("creation_time")
    val creationTime: Int,
    @JsonNames("latest_activity")
    val latestActivity: Int,
    @JsonNames("clan_id")
    val clanId: Int?,
    @JsonNames("clan_priv")
    val clanPriv: Int?,
    @JsonNames("preferred_mode")
    val preferredMode: Int,
    @JsonNames("play_style")
    val playStyle: Int,
    @JsonNames("custom_badge_name")
    val customBadgeName: String?,
    @JsonNames("custom_badge_icon")
    val customBadgeIcon: String?,
    @JsonNames("userpage_content")
    val userpageContent: String?,
)
