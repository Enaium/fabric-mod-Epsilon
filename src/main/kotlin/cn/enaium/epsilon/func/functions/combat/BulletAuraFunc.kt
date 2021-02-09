package cn.enaium.epsilon.func.functions.combat

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.Event
import cn.enaium.epsilon.event.events.MotionEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.setting.settings.FloatSetting
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
class BulletAuraFunc : Func("BulletAura", 0, Category.COMBAT) {
    private var target: Entity? = null

    private val range = FloatSetting(this, "Range", 4.1f, 0.1f, 7.0f)

    private val delay = EnableSetting(this, "Delay", false)

    private val shulkerBullet = EnableSetting(this, "ShulkerBullet", true)

    private val fireball = EnableSetting(this, "Fireball", true)

    private val dragonFireball = EnableSetting(this, "DragonFireball", true)

    fun onMotion(motionEvent: MotionEvent) {
        target = when (motionEvent.type) {
            Event.Type.PRE -> {

                if(delay.enable) if (Epsilon.MC.player!!.getAttackCooldownProgress(0f) < 1) return

                if (getTargets().isNotEmpty()) getTargets().sortedBy { Epsilon.MC.player!!.squaredDistanceTo(it) }[0] else null
            }
            Event.Type.POST -> {
                if (target == null) return
                Epsilon.MC.interactionManager!!.attackEntity(Epsilon.MC.player, target)
                Epsilon.MC.player!!.swingHand(Hand.MAIN_HAND)
                null
            }
        }
    }

    private fun getTargets(): ArrayList<Entity> {
        val entityList: ArrayList<Entity> = ArrayList()
        for (entity in Epsilon.MC.world!!.entities) {
            if (Epsilon.MC.player!!.squaredDistanceTo(entity) <= (range.current * range.current)) {
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