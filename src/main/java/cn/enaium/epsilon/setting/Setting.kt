package cn.enaium.epsilon.setting

import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.utils.I18nUtils

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Setting(var module: Module, var name: String) {
    open fun getDisplayName(): String {
        return I18nUtils.getKey("module." + module.category.name + ".setting." + name)
    }
}