package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class GlowFunc : Func("Glow", 0, Category.RENDER) {

    @EventAT
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