package cn.enaium.epsilon.client.func.functions.other

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.MotioningEvent
import net.minecraft.client.gui.screen.DeathScreen

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("AutoRespawn", category = Category.OTHER)
class RespawnFunc {
    @Event
    fun on(motioningEvent: MotioningEvent) {
        if (MC.currentScreen is DeathScreen) {
            MC.player!!.requestRespawn()
            MC.openScreen(null)
        }
    }
}