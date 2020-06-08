package cn.enaium.epsilon.func.functions.movement

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class NoFallFunc : Func("NoFall", 0, Category.MOVEMENT) {
    fun on(updateEvent: UpdateEvent) {
        if (MC.player!!.fallDistance <= 2) return

        MC.player!!.networkHandler.sendPacket(PlayerMoveC2SPacket(true))
    }
}