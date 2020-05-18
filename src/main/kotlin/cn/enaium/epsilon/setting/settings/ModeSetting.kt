package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.Setting
import kotlin.collections.ArrayList

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ModeSetting(func: Func, name: String, var current: String, var modes: ArrayList<String>) : Setting(func, name) {
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