package cn.enaium.epsilon.client.func.functions.movement

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.event.events.UpdateEvent
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("NoFall", category = Category.MOVEMENT)
class NoFallFunc {

    @Event
    fun on(updateEvent: UpdateEvent) {
        if (MC.player!!.fallDistance <= 2) return

        MC.player!!.networkHandler.sendPacket(PlayerMoveC2SPacket(true))
    }
}