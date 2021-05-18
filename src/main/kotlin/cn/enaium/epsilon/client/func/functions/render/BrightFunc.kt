package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.Rendering2DEvent
import cn.enaium.epsilon.client.setting.ModeSetting
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Bright", category = Category.RENDER)
class BrightFunc {

    @Setting("Mode")
    private val mode = ModeSetting("Gamma", arrayListOf("Gamma", "NightVision"))

    @Event
    fun on(rendering2DEvent: Rendering2DEvent) {
        when (mode.current) {
            "Gamma" -> MC.options.gamma = 300.0
            "NightVision" -> MC.player!!.addStatusEffect(
                StatusEffectInstance(
                    StatusEffects.NIGHT_VISION,
                    16360,
                    0,
                    false,
                    false
                )
            )
        }
    }

    @Disable
    fun onDisable() {
        MC.options.gamma = 1.0
        MC.player!!.removeStatusEffectInternal(StatusEffects.NIGHT_VISION)
    }
}