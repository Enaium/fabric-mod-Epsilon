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
    private val ironGolem = SettingEnable(this, "ironGolem", false)
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
                val stream: Stream<LivingEntity> = StreamSupport.stream(MC.world!!.entities.spliterator(), true).filter {
                    isTarget(it)
                }.filter {
                    it is LivingEntity
                }.map {
                    it as LivingEntity
                }.filter {
                    it != MC.player && !it.removed && it.health > 0 && MC.player!!.squaredDistanceTo(it) <= (range.current * range.current)
                }

                target = stream.min(Comparator.comparingDouble() { it.health.toDouble() }).orElse(null)

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
        if (!player.enable && e is PlayerEntity) {
            return false
        }
        if (!animal.enable && (e is AnimalEntity || e is AmbientEntity || e is WaterCreatureEntity)) {
            return false
        }
        if (!mob.enable && e is MobEntity) {
            return false
        }
        return true
    }
}