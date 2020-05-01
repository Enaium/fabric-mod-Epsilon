package cn.enaium.epsilon.module.modules.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class BrightModule : Module("Bright", 0, Category.RENDER) {
    override fun onEnable() {
        Epsilon.MC.options.gamma = 300.0
        super.onEnable()
    }

    override fun onDisable() {
        Epsilon.MC.options.gamma = 1.0
        super.onDisable()
    }
}