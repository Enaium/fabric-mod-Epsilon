package cn.enaium.epsilon.module.modules.combat

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.Event
import cn.enaium.epsilon.event.EventTarget
import cn.enaium.epsilon.event.events.EventMotion
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.setting.settings.SettingEnable
import cn.enaium.epsilon.setting.settings.SettingFloat
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Hand
import org.lwjgl.glfw.GLFW


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class AuraModule : Module("Aura", GLFW.GLFW_KEY_R, Category.COMBAT) {
    private val range = SettingFloat(this, "Range", 4.1f, 0.1f, 7.0f)
    private val player = SettingEnable(this, "Player", true)
    private val animal = SettingEnable(this, "Animal", false)
    private val mob = SettingEnable(this, "Mob", true)
    private val wolf = SettingEnable(this, "Wolf", false)
    private val villager = SettingEnable(this, "Villager", false)
    private val ironGolem = SettingEnable(this, "IronGolem", false)
    private val endermen = SettingEnable(this, "Endermen", false)
    private val endermite = SettingEnable(this, "Endermite", false)
    private val zombiePigman = SettingEnable(this, "ZombiePigman", false)
    private val zombieVillager = SettingEnable(this, "VillagerPigman", false)
    private var target: LivingEntity? = null

    init {
        addSettings(listOf(range, player, animal, mob, villager, ironGolem, wolf, endermen, endermite, zombiePigman, zombieVillager))
    }

    @EventTarget
    fun onMotion(eventMotion: EventMotion) {
        when (eventMotion.type) {
            Event.Type.PRE -> {

                if (MC.player!!.getAttackCooldownProgress(0f) < 1) return

                val targets = getTargets()
                if (targets.size > 0) {
                    target = getTargets()[0]
                }
            }
            Event.Type.POST -> {

                if (target == null) return
                MC.interactionManager!!.attackEntity(MC.player, target)
                MC.player!!.swingHand(Hand.MAIN_HAND)
                target = null

            }
        }
    }

    private fun getTargets(): ArrayList<LivingEntity> {
        val targets: ArrayList<LivingEntity> = ArrayList()
        for (o in MC.world!!.entities) {
            if (o is LivingEntity) {
                if (isTarget(o)) {
                    targets.add(o)
                }
            }
        }
        return targets
    }

    private fun isTarget(e: Entity): Boolean {
        return e is LivingEntity && e != MC.player && !e.removed && e.health > 0 && MC.player!!.squaredDistanceTo(e) <= (range.current * range.current)
    }
}