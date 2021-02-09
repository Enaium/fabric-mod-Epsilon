package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class GlowFunc : Func("Glow", 0, Category.RENDER) {
    fun onUpdate(updateEvent: UpdateEvent) {
        for (e in Epsilon.MC.world!!.entities) {
            e.isGlowing = true
        }
    }

    override fun onDisable() {
        super.onDisable()
        for (e in Epsilon.MC.world!!.entities) {
            e.isGlowing = false
        }
    }
}