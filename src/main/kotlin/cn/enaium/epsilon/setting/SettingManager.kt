package cn.enaium.epsilon.setting

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.setting.settings.*
import java.util.*

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class SettingManager {
    var settings: ArrayList<Setting> = ArrayList()

    fun load() {
        for (module in Epsilon.moduleManager.modules) {
            for (field in module.javaClass.declaredFields) {
                if (field.isAnnotationPresent(SettingAT::class.java)) {
                    field.isAccessible = true
                    when (field.type) {
                        SettingEnable::class.java -> {
                            settings.add(field.get(module) as SettingEnable)
                        }
                        SettingInteger::class.java -> {
                            settings.add(field.get(module) as SettingInteger)
                        }
                        SettingFloat::class.java -> {
                            settings.add(field.get(module) as SettingFloat)
                        }
                        SettingDouble::class.java -> {
                            settings.add(field.get(module) as SettingDouble)
                        }
                        SettingLong::class.java -> {
                            settings.add(field.get(module) as SettingLong)
                        }
                        SettingMode::class.java -> {
                            settings.add(field.get(module) as SettingMode)
                        }
                    }
                }
            }
        }
    }

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