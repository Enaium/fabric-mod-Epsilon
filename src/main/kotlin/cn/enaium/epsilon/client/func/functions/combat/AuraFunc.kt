package cn.enaium.epsilon.client.func.functions.combat

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.cf4m
import cn.enaium.cf4m.event.Listener.At
import cn.enaium.epsilon.client.events.MotionEvent
import cn.enaium.epsilon.client.utils.RotationUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.*
import net.minecraft.entity.passive.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import org.lwjgl.glfw.GLFW
import java.util.stream.Stream
import java.util.stream.StreamSupport


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Aura", key = GLFW.GLFW_KEY_R, category = Category.COMBAT)
class AuraFunc {
    private val range = FloatSetting(this, "Range", "", 4.1f, 0.1f, 7.0f)

    private val priority =
        ModeSetting(this, "Priority", "", "Distance", arrayListOf("Distance", "Fov", "Angle", "Health"))

    private val aim = EnableSetting(this, "Aim", "Auto Aim", false)

    private val player = EnableSetting(this, "Player", "Attack player", true)

    private val animal = EnableSetting(this, "Animal", "Attack animal", false)

    private val wolf = EnableSetting(this, "Wolf", "Attack wolf", false)

    private val villager = EnableSetting(this, "Villager", "Attack villager", false)

    private val ironGolem = EnableSetting(this, "IronGolem", "Attack ironGolem", false)

    private val llama = EnableSetting(this, "Llama", "Attack llama", false)

    private val fox = EnableSetting(this, "Fox", "Attack fox", false)

    private val strider = EnableSetting(this, "Strider", "Attack fox", false)

    private val enderman = EnableSetting(this, "Enderman", "Attack enderman", false)

    private val endermite = EnableSetting(this, "Endermite", "Attack endermite", false)

    private val zombieVillager = EnableSetting(this, "ZombieVillager", "Attack zombie villager", false)

    private val zombiePigman = EnableSetting(this, "ZombiePigman", "Attack zombie pigman", false)

    private var target: LivingEntity? = null

    @Event
    fun onMotion(motionEvent: MotionEvent) {
        cf4m.module.setValue(this, "tag", priority.current)
        when (motionEvent.at) {
            At.HEAD -> {
                if (MC.player!!.getAttackCooldownProgress(0f) < 1) return

                target = when (priority.current) {
                    "Distance" -> getTargets().min(Comparator.comparingDouble { MC.player!!.squaredDistanceTo(it) })
                        .orElse(null)
                    "Fov" -> getTargets().min(Comparator.comparingDouble {
                        RotationUtils.getDistanceBetweenAngles(it.boundingBox.center)
                    }).orElse(null)
                    "Angle" -> getTargets().min(Comparator.comparingDouble {
                        RotationUtils.getAngleToLookVec(it.boundingBox.center)
                    }).orElse(null)
                    else -> getTargets().min(Comparator.comparingDouble { it.health.toDouble() }).orElse(null)
                }
            }
            At.TAIL -> {
                if (target == null) return

                if (aim.enable) {
                    motionEvent.yaw =
                        RotationUtils.getNeededRotations(RotationUtils.getRandomCenter(target!!.boundingBox)).yaw
                    motionEvent.pitch =
                        RotationUtils.getNeededRotations(RotationUtils.getRandomCenter(target!!.boundingBox)).pitch
                }

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
        if (!llama.enable) {
            s = s.filter { it !is LlamaEntity }
        }
        if (!fox.enable) {
            s = s.filter { it !is FoxEntity }
        }
        if (!strider.enable) {
            s = s.filter { it !is StriderEntity }
        }
        if (!enderman.enable) {
            s = s.filter { it !is EndermanEntity }
        }
        if (!endermite.enable) {
            s = s.filter { it !is EndermiteEntity }
        }
        if (!zombieVillager.enable) {
            s = s.filter { it !is ZombieVillagerEntity }
        }
        if (!zombiePigman.enable) {
            s = s.filter { it !is ZombifiedPiglinEntity }
        }
        return s
    }

    private fun isTarget(e: LivingEntity): Boolean {
        return e != MC.player && !e.isRemoved && e.health > 0 && MC.player!!.squaredDistanceTo(e) <= (range.current * range.current)
    }
}