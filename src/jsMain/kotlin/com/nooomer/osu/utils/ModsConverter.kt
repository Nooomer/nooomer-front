package com.nooomer.osu.utils

import kotlin.js.Date

object ModsConverter {
    val osuMods: MutableMap<String, Int> = LinkedHashMap()
    var timeSums = 0.0
    private var measureTime = true
    var begin: Double = 0.0
    var end: Double = 0.0
    init {
        osuMods["NF"] = 1
        osuMods["EZ"] = 2
        osuMods["TD"] = 4
        osuMods["HD"] = 8
        osuMods["HR"] = 16
        osuMods["SD"] = 32
        osuMods["DT"] = 64
        osuMods["RX"] = 128
        osuMods["HT"] = 256
        osuMods["NC"] = 512// Only set along with DoubleTime. i.e: NC only gives 576
        osuMods["FL"] = 1024
        osuMods["APLAY"] = 2048
        osuMods["SO"] = 4096
        osuMods["AP"] = 8192// Autopilot
        osuMods["PF"] = 16384// Only set along with SuddenDeath. i.e: PF only gives 16416
        osuMods["K4"] = 32768
        osuMods["K5"] = 65536
        osuMods["K6"] = 131072
        osuMods["K7"] = 262144
        osuMods["K8"] = 524288
        osuMods["FI"] = 1048576
        osuMods["RD"] = 2097152
        osuMods["CM"] = 4194304
        osuMods["TG"] = 8388608
        osuMods["K9"] = 16777216
        osuMods["KC"] = 33554432
        osuMods["K1"] = 67108864
        osuMods["K3"] = 134217728
        osuMods["K2"] = 268435456
        osuMods["SV2"] = 536870912
        osuMods["MRR"] = 1073741824
    }

    fun endTimeMEasure(){
        measureTime = !measureTime
    }

    fun convert(bitVal: Int): String {
        var mods = ""
        var i: Int = bitVal
        val iter = ArrayList(osuMods.entries).listIterator(osuMods.size)
        if(begin == 0.0) {
            begin = Date.now()
        }
            while (iter.hasPrevious()) {
                val entry = iter.previous()
                if (i >= entry.value) {
                    i -= entry.value

                    if (entry.key == "NC") {
                        i -= osuMods["DT"]!!
                    }
                    mods = entry.key + mods
                }
            }
        if(!measureTime){
            end = Date.now()
            console.info("All convert elapsed time: ${end - begin}ms")
            begin = 0.0
            end = 0.0
            measureTime = !measureTime
        }
        return mods
    }
}