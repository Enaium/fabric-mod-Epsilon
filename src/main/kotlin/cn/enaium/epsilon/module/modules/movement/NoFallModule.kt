package cn.enaium.epsilon.module.modules.movement

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class NoFallModule : Module("NoFall", 0, Category.MOVEMENT) {
    @EventAT
    fun on(updateEvent: UpdateEvent) {
        if (MC.player!!.fallDistance <= 2) return

        MC.player!!.networkHandler.sendPacket(PlayerMoveC2SPacket(true))
    }
}