package cn.enaium.epsilon.client.func.functions.combat

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.cf4m
import cn.enaium.cf4m.event.Listener.At
import cn.enaium.epsilon.client.events.MotionEvent
import cn.enaium.epsilon.client.settings.*
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
    @Setting("Range")
    private val range = FloatSetting(4.1f, 0.1f, 7.0f)

    @Setting("Priority")
    private val priority =
        ModeSetting("Distance", arrayListOf("Distance", "Fov", "Angle", "Health"))

    @Setting("Aim", description = "Auto Aim")
    private val aim = EnableSetting(false)

    @Setting("Player")
    private val player = EnableSetting(true)

    @Setting("Animal")
    private val animal = EnableSetting(false)

    @Setting("Wolf")
    private val wolf = EnableSetting(false)

    @Setting("Villager")
    private val villager = EnableSetting(false)

    @Setting("IronGolem")
    private val ironGolem = EnableSetting(false)

    @Setting("Llama")
    private val llama = EnableSetting(false)

    @Setting("Fox")
    private val fox = EnableSetting(false)

    @Setting("Strider")
    private val strider = EnableSetting(false)

    @Setting("Enderman")
    private val enderman = EnableSetting(false)

    @Setting("Endermite")
    private val endermite = EnableSetting(false)

    @Setting("ZombieVillager")
    private val zombieVillager = EnableSetting(false)

    @Setting("ZombiePigman")
    private val zombiePigman = EnableSetting(false)

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