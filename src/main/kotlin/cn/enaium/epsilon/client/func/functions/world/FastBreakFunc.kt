package cn.enaium.epsilon.client.func.functions.world

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.event.events.UpdateEvent
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.IMC
import cn.enaium.epsilon.client.events.BlockBreakingProgressEvent
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import org.lwjgl.glfw.GLFW


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("FastBreak", key = GLFW.GLFW_KEY_B, category = Category.OTHER)
class FastBreakFunc {

    @Event
    fun onUpdate(updateEvent: UpdateEvent) {
        IMC.interactionManager.blockBreakingCooldown = 0
    }

    @Event
    fun onBlockBreakingProgress(breakingProgressEvent: BlockBreakingProgressEvent) {
        if (IMC.interactionManager.currentBreakingProgress >= 1)
            return
        val action: Action = Action.STOP_DESTROY_BLOCK
        val blockPos: BlockPos = breakingProgressEvent.blockPos
        val direction: Direction = breakingProgressEvent.direction
        IMC.interactionManager.invokeSendPlayerAction(action, blockPos, direction)
    }
}