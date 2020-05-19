package cn.enaium.epsilon.func.functions.other

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT
import net.minecraft.client.gui.screen.DeathScreen

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class RespawnFunc : Func("AutoRespawn", 0, Category.OTHER) {
    @EventAT
    fun on(updateEvent: UpdateEvent) {
        if (MC.currentScreen is DeathScreen) {
            MC.player!!.requestRespawn()
            MC.openScreen(null)
        }
    }
}