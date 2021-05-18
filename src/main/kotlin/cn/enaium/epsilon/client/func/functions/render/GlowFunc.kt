package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.epsilon.client.events.MotioningEvent
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Glow", category = Category.RENDER)
class GlowFunc {
    @Event
    fun onUpdate(motioningEvent: MotioningEvent) {
        for (e in MC.world!!.entities) {
            e.isGlowing = true
        }
    }

    @Disable
    fun onDisable() {
        for (e in MC.world!!.entities) {
            e.isGlowing = false
        }
    }
}