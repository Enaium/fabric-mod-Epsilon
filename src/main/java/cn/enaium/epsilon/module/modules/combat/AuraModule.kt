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
    private val wolf = SettingEnable(this, "Wolf", false)
    private val villager = SettingEnable(this, "Villager", false)
    private val ironGolem = SettingEnable(this, "IronGolem", false)
    private val endermen = SettingEnable(this, "Endermen", false)
    private val endermite = SettingEnable(this, "Endermite", false)
    private val zombiePigman = SettingEnable(this, "ZombiePigman", false)
    private val zombieVillager = SettingEnable(this, "VillagerPigman", false)
    private var target: LivingEntity? = null

    init {
        addSettings(listOf(range, player, animal, villager, ironGolem, wolf, endermen, endermite, zombiePigman, zombieVillager))
    }

    @EventTarget
    fun onMotion(eventMotion: EventMotion) {
        when (eventMotion.type) {
            Event.Type.PRE -> {

                if (MC.player!!.getAttackCooldownProgress(0f) < 1) return

                target = getTargets().min(Comparator.comparingDouble() { it.health.toDouble() }).orElse(null)

            }
            Event.Type.POST -> {
                if (target == null) return
                MC.interactionManager!!.attackEntity(MC.player, target)
                MC.player!!.swingHand(Hand.MAIN_HAND)
                target = null
            }
        }
    }

    private fun getTargets(): Stream<LivingEntity> {
        var s = StreamSupport.stream(MC.world!!.entities.spliterator(), true).filter {
            it is LivingEntity
        }.map { it as LivingEntity }.filter {
            isTarget(it)
        }
        if (!player.enable) {
            s = s.filter { it !is PlayerEntity }
        }
        if (!animal.enable) {
            s = s.filter { it !is AnimalEntity }
        }
        if (!wolf.enable) {
            s = s.filter { it !is WolfEntity }
        }
        if (!villager.enable) {
            s = s.filter { it !is VillagerEntity }
        }
        if (!ironGolem.enable) {
            s = s.filter { it !is IronGolemEntity }
        }
        if (!endermen.enable) {
            s = s.filter { it !is EndermanEntity }
        }
        if (!endermite.enable) {
            s = s.filter { it !is EndermiteEntity }
        }
        if (!zombieVillager.enable) {
            s = s.filter { it !is ZombieVillagerEntity }
        }
        if (!zombiePigman.enable) {
            s = s.filter { it !is ZombiePigmanEntity }
        }
        return s
    }

    private fun isTarget(e: LivingEntity): Boolean {
        return e != MC.player && !e.removed && e.health > 0 && MC.player!!.squaredDistanceTo(e) <= (range.current * range.current)
    }
}