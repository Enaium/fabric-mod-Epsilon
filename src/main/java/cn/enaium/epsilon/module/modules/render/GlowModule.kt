package cn.enaium.epsilon.module.modules.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventTarget
import cn.enaium.epsilon.event.events.EventUpdate
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class GlowModule : Module("Glow", 0, Category.RENDER) {
    @EventTarget
    fun onUpdate(eventUpdate: EventUpdate) {
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