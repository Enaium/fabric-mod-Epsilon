package cn.enaium.epsilon.func.functions.world

import cn.enaium.epsilon.IMC
import cn.enaium.epsilon.event.events.BlockBreakingProgressEvent
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import org.lwjgl.glfw.GLFW


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class FastBreakFunc : Func("FastBreak", GLFW.GLFW_KEY_B, Category.WORLD) {
    fun onUpdate(updateEvent: UpdateEvent) {
        IMC.interactionManager.blockBreakingCooldown = 0
    }

    fun onBlockBreakingProgress(breakingProgressEvent: BlockBreakingProgressEvent) {
        if (IMC.interactionManager.currentBreakingProgress >= 1)
            return
        val action: Action = Action.STOP_DESTROY_BLOCK
        val blockPos: BlockPos = breakingProgressEvent.blockPos
        val direction: Direction = breakingProgressEvent.direction
        IMC.interactionManager.invokeSendPlayerAction(action, blockPos, direction)
    }
}