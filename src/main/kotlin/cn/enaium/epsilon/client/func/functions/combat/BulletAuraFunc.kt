package cn.enaium.epsilon.client.func.functions.combat

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.MC
import cn.enaium.cf4m.event.EventBase.Type
import cn.enaium.epsilon.client.events.MotionEvent
import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.DragonFireballEntity
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.entity.projectile.ShulkerBulletEntity
import net.minecraft.util.Hand

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("BulletAura", category = Category.COMBAT)
class BulletAuraFunc {
    private var target: Entity? = null

    private val range = FloatSetting(this, "Range", "Range", 4.1f, 0.1f, 7.0f)

    private val delay = EnableSetting(this, "Delay", "Delay", false)

    private val shulkerBullet = EnableSetting(this, "ShulkerBullet", "Attack ShulkerBullet", true)

    private val fireball = EnableSetting(this, "Fireball", "Attack Fireball", true)

    private val dragonFireball = EnableSetting(this, "DragonFireball", "Attack DragonFireball", true)

    @Event
    fun onMotion(motionEvent: MotionEvent) {
        target = when (motionEvent.type) {
            Type.PRE -> {

                if (delay.enable) if (MC.player!!.getAttackCooldownProgress(0f) < 1) return

                if (getTargets().isNotEmpty()) getTargets().sortedBy { MC.player!!.squaredDistanceTo(it) }[0] else null
            }
            Type.POST -> {
                if (target == null) return
                MC.interactionManager!!.attackEntity(MC.player, target)
                MC.player!!.swingHand(Hand.MAIN_HAND)
                null
            }
        }
    }

    private fun getTargets(): ArrayList<Entity> {
        val entityList: ArrayList<Entity> = ArrayList()
        for (entity in MC.world!!.entities) {
            if (MC.player!!.squaredDistanceTo(entity) <= (range.current * range.current)) {
                when (entity) {
                    is ShulkerBulletEntity -> if (shulkerBullet.enable) entityList.add(entity)
                    is FireballEntity -> if (fireball.enable) entityList.add(entity)
                    is DragonFireballEntity -> if (dragonFireball.enable) entityList.add(entity)
                }
            }
        }
        return entityList
    }
}