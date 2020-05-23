package cn.enaium.epsilon.func.functions.combat

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.Event
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.MotionEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT
import cn.enaium.epsilon.setting.SettingAT
import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.setting.settings.FloatSetting
import cn.enaium.epsilon.setting.settings.ModeSetting
import cn.enaium.epsilon.utils.RotationUtils
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
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class AuraFunc : Func("Aura", GLFW.GLFW_KEY_R, Category.COMBAT) {

    @SettingAT
    private val range = FloatSetting(this, "Range", 4.1f, 0.1f, 7.0f)

    @SettingAT
    private val priority = ModeSetting(this, "Priority", "Distance", arrayListOf("Distance", "Fov", "Angle", "Health"))

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
    private val llama = EnableSetting(this, "Llama", false)

    @SettingAT
    private val fox = EnableSetting(this, "Fox", false)

    @SettingAT
    private val strider = EnableSetting(this, "Strider", false)

    @SettingAT
    private val enderman = EnableSetting(this, "Enderman", false)

    @SettingAT
    private val endermite = EnableSetting(this, "Endermite", false)

    @SettingAT
    private val zombieVillager = EnableSetting(this, "ZombieVillager", false)

    @SettingAT
    private val zombiePigman = EnableSetting(this, "ZombiePigman", false)

    private var target: LivingEntity? = null

    @EventAT
    fun onMotion(motionEvent: MotionEvent) {
        tag = priority.current
        target = when (motionEvent.type) {
            Event.Type.PRE -> {

                if (MC.player!!.getAttackCooldownProgress(0f) < 1) return

                when (priority.current) {
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
            Event.Type.POST -> {
                if (target == null) return
                MC.interactionManager!!.attackEntity(MC.player, target)
                MC.player!!.swingHand(Hand.MAIN_HAND)
                null
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
        return e != MC.player && !e.removed && e.health > 0 && MC.player!!.squaredDistanceTo(e) <= (range.current * range.current)
    }
}