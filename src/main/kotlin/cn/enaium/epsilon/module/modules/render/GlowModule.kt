package cn.enaium.epsilon.module.modules.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.EventUpdate
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import cn.enaium.epsilon.setting.SettingAT
import cn.enaium.epsilon.setting.settings.EnableSetting
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.passive.WolfEntity
import net.minecraft.entity.player.PlayerEntity
import java.util.stream.Stream
import java.util.stream.StreamSupport

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class GlowModule : Module("Glow", 0, Category.RENDER) {

    @EventAT
    fun onUpdate(eventUpdate: EventUpdate) {
        for (e in Epsilon.MC.world!!.entities) {
            e.isGlowing = true
        }
    }

    override fun onDisable() {
        super.onDisable()
        for (e in Epsilon.MC.world!!.entities) {
            e.isGlowing = false
        }
    }
}