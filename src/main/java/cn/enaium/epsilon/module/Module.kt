package cn.enaium.epsilon.module

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.SettingManager

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Module(var name: String, var keyCode: Int, var category: Category) {

    var enable = false
    var tag = ""

    fun addSetting(setting: Setting) {
        SettingManager.settings.add(setting)
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


    open fun getDisplayTag(): String {
        return if (tag == "") {
            name
        } else {
            return "$name:$tag"
        }
    }


}