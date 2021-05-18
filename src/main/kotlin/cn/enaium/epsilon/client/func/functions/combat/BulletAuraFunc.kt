package cn.enaium.epsilon.client.func.functions.combat

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.MotionedEvent
import cn.enaium.epsilon.client.events.MotioningEvent
import cn.enaium.epsilon.client.setting.*
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

    @Setting("Range")
    private val range = FloatSetting(4.1f, 0.1f, 7.0f)

    @Setting("Delay")
    private val delay = EnableSetting(false)

    @Setting("ShulkerBullet")
    private val shulkerBullet = EnableSetting(true)

    @Setting("Fireball")
    private val fireball = EnableSetting(true)

    @Setting("DragonFireball")
    private val dragonFireball = EnableSetting(true)

    @Event
    fun onMotion(motioningEvent: MotioningEvent) {


        if (delay.enable) if (MC.player!!.getAttackCooldownProgress(0f) < 1) return

        target = if (getTargets().isNotEmpty()) getTargets().sortedBy { MC.player!!.squaredDistanceTo(it) }[0] else null

    }

    @Event
    fun onMotion(motionedEvent: MotionedEvent) {
        if (target == null) return
        MC.interactionManager!!.attackEntity(MC.player, target)
        MC.player!!.swingHand(Hand.MAIN_HAND)
        target = null
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