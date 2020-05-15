package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.setting.Setting
import java.util.*
import kotlin.collections.ArrayList

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ModeSetting(module: Module, name: String, var current: String, var modes: ArrayList<String>) : Setting(module, name) {
    fun getCurrentIndex(): Int {
        var index = 0
        for (s in modes) {
            if (s.equals(current, ignoreCase = true)) {
                return index
            }
            index++
        }
        return index
    }
}