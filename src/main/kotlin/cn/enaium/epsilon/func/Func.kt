package cn.enaium.epsilon.func

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.setting.Setting

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Func(var name: String, var keyCode: Int, var category: Category) {

    var enable = false
    var tag = ""

    fun addSettings(setting: List<Setting>) {
        Epsilon.settingManager.settings.addAll(setting)
    }

    fun enable() {
        enable = !enable
        if (enable) onEnable() else onDisable()
    }

    open fun onEnable() {
        Epsilon.eventManager.register(this)
    }

    open fun onDisable() {
        Epsilon.eventManager.unregister(this)
    }


    fun getDisplayTag(): String {
        return if (tag == "") {
            name
        } else {
            return "$name:$tag"
        }
    }
}