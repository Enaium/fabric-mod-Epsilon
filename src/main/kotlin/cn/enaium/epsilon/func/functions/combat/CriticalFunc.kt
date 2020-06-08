package cn.enaium.epsilon.func.functions.combat

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.ModeSetting
import net.minecraft.entity.LivingEntity
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class CriticalFunc : Func("Critical", 0, Category.COMBAT) {
    private val mode = ModeSetting(this, "Mode", "Packet", arrayListOf("Packet", "LowJump", "Jump"))

    fun on(updateEvent: UpdateEvent) {

        if (MC.crosshairTarget == null || MC.crosshairTarget!!.type != HitResult.Type.ENTITY || (MC.crosshairTarget as EntityHitResult).entity !is LivingEntity)
            return

        if (!MC.player!!.isOnGround)
            return

        if (MC.player!!.isTouchingWater || MC.player!!.isInLava)
            return

        when (mode.current) {
            "Packet" -> {
                val posX = MC.player!!.x
                val posY = MC.player!!.y
                val posZ = MC.player!!.z

                sendPos(posX, posY + 0.0625, posZ, true)
                sendPos(posX, posY, posZ, false)
                sendPos(posX, posY + 1.1E-5, posZ, false)
                sendPos(posX, posY, posZ, false)
            }
            "LowJump" -> {
                MC.player!!.addVelocity(0.0, 0.1, 0.0);
                MC.player!!.fallDistance = 0.1F
                MC.player!!.isOnGround = false
            }
            "Jump" -> {
                MC.player!!.jump()
            }
        }
    }

    private fun sendPos(x: Double, y: Double, z: Double, onGround: Boolean) {
        MC.player!!.networkHandler.sendPacket(
            PositionOnly(x, y, z, onGround)
        )
    }
}