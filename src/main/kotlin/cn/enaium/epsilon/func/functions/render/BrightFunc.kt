package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.events.Render2DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.ModeSetting
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class BrightFunc : Func("Bright", 0, Category.RENDER) {
    private val mode = ModeSetting(this, "Mode", "Gamma", arrayListOf("Gamma", "NightVision"))

    fun on(render2DEvent: Render2DEvent) {
        when (mode.current) {
            "Gamma" -> MC.options.gamma = 300.0
            "NightVision" -> MC.player!!.addStatusEffect(StatusEffectInstance(StatusEffects.NIGHT_VISION, 16360, 0, false, false))
        }
    }

    override fun onDisable() {
        super.onDisable()
        MC.options.gamma = 1.0
        MC.player!!.removeStatusEffectInternal(StatusEffects.NIGHT_VISION)
    }
}