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
import net.minecraft.entity.mob.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.passive.WolfEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import org.lwjgl.glfw.GLFW
import java.util.stream.Stream
import java.util.stream.StreamSupport

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
                val any = StreamSupport.stream(MC.world!!.entities.spliterator(), true).filter {
                    isTarget(it)
                }.sorted(Comparator.comparingDouble { MC.player!!.squaredDistanceTo(it) }).toArray()

                if (any.isNotEmpty() && target == null) {
                    target = any[0] as LivingEntity
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

    private fun isTarget(e: Entity): Boolean {
        return e is LivingEntity && e != MC.player && !e.removed && (e as LivingEntity).health > 0 && MC.player!!.squaredDistanceTo(e) <= (range.current * range.current)
    }
}