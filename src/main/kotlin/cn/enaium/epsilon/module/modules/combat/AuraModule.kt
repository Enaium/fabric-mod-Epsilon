package cn.enaium.epsilon.module.modules.combat

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.Event
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.EventMotion
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import cn.enaium.epsilon.setting.SettingAT
import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.setting.settings.FloatSetting
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
@ModuleAT
class AuraModule : Module("Aura", GLFW.GLFW_KEY_R, Category.COMBAT) {

    @SettingAT
    private val range = FloatSetting(this, "Range", 4.1f, 0.1f, 7.0f)
    @SettingAT
    private val player = EnableSetting(this, "Player", true)
    @SettingAT
    private val animal = EnableSetting(this, "Animal", false)
    @SettingAT
    private val wolf = EnableSetting(this, "Wolf", false)
    @SettingAT
    private val villager = EnableSetting(this, "Villager", false)
    @SettingAT
    private val ironGolem = EnableSetting(this, "IronGolem", false)
    @SettingAT
    private val endermen = EnableSetting(this, "Endermen", false)
    @SettingAT
    private val endermite = EnableSetting(this, "Endermite", false)
    @SettingAT
    private val zombieVillager = EnableSetting(this, "VillagerPigman", false)
    private var target: LivingEntity? = null

    @EventAT
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
        return s
    }

    private fun isTarget(e: LivingEntity): Boolean {
        return e != MC.player && !e.removed && e.health > 0 && MC.player!!.squaredDistanceTo(e) <= (range.current * range.current)
    }
}