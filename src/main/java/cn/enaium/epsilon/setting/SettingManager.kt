package cn.enaium.epsilon.setting

import cn.enaium.epsilon.module.Module
import java.util.*

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object SettingManager {
    var settings: ArrayList<Setting> = ArrayList()

    fun getSetting(m: Module, name: String): Setting? {
        for (s in settings) {
            if (s.module == m && s.name.equals(name, ignoreCase = true)) {
                return s
            }
        }
        return null
    }

    fun getSettingsForModule(m: Module): ArrayList<Setting>? {
        val settings = ArrayList<Setting>()
        for (s in this.settings) {
            if (s.module == m) settings.add(s)
        }
        return if (settings.isEmpty()) null else settings
    }
}