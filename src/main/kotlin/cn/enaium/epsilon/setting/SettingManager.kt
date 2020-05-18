package cn.enaium.epsilon.setting

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.func.Func
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
        for (module in Epsilon.funcManager.funcs) {
            for (field in module.javaClass.declaredFields) {
                if (field.isAnnotationPresent(SettingAT::class.java)) {
                    field.isAccessible = true
                    when (field.type) {
                        EnableSetting::class.java -> {
                            settings.add(field.get(module) as EnableSetting)
                        }
                        IntegerSetting::class.java -> {
                            settings.add(field.get(module) as IntegerSetting)
                        }
                        FloatSetting::class.java -> {
                            settings.add(field.get(module) as FloatSetting)
                        }
                        DoubleSetting::class.java -> {
                            settings.add(field.get(module) as DoubleSetting)
                        }
                        LongSetting::class.java -> {
                            settings.add(field.get(module) as LongSetting)
                        }
                        ModeSetting::class.java -> {
                            settings.add(field.get(module) as ModeSetting)
                        }
                    }
                }
            }
        }
    }

    fun getSetting(m: Func, name: String): Setting? {
        for (s in settings) {
            if (s.func == m && s.name.equals(name, ignoreCase = true)) {
                return s
            }
        }
        return null
    }

    fun getSettingsForModule(m: Func): ArrayList<Setting>? {
        val settings = ArrayList<Setting>()
        for (s in this.settings) {
            if (s.func == m) settings.add(s)
        }
        return if (settings.isEmpty()) null else settings
    }
}