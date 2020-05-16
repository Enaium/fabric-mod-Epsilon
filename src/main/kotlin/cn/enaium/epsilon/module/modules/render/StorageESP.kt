package cn.enaium.epsilon.module.modules.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.EventRender3D
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import cn.enaium.epsilon.setting.SettingAT
import cn.enaium.epsilon.setting.settings.EnableSetting
import net.minecraft.block.entity.*
import java.util.stream.Stream
import java.util.stream.StreamSupport

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class StorageESP : Module("StorageESP", 0, Category.RENDER) {

    @SettingAT
    private val chest = EnableSetting(this, "Chest", true)

    @SettingAT
    private val trappedChest = EnableSetting(this, "TrappedChest", true)

    @SettingAT
    private val enderChest = EnableSetting(this, "EnderChest", true)

    @SettingAT
    private val shulkerBoxChest = EnableSetting(this, "ShulkerBoxChest", true)

    @SettingAT
    private val barrel = EnableSetting(this, "Barrel", true)

    @SettingAT
    private val hopper = EnableSetting(this, "Hopper", true)

    @SettingAT
    private val furnace = EnableSetting(this, "Furnace", true)

    @EventAT
    fun onRender(eventRender3D: EventRender3D) {

    }

    private fun getTargets(): Stream<BlockEntity> {
        var s = StreamSupport.stream(Epsilon.MC.world!!.blockEntities.spliterator(), true)
        if (!chest.enable) {
            s = s.filter { it !is ChestBlockEntity }
        }
        if (!trappedChest.enable) {
            s = s.filter { it !is TrappedChestBlockEntity }
        }
        if (!enderChest.enable) {
            s = s.filter { it !is EnderChestBlockEntity }
        }
        if (!shulkerBoxChest.enable) {
            s = s.filter { it !is ShulkerBoxBlockEntity }
        }
        if (!barrel.enable) {
            s = s.filter { it !is BarrelBlockEntity }
        }
        if (!hopper.enable) {
            s = s.filter { it !is HopperBlockEntity }
        }
        if (!furnace.enable) {
            s = s.filter { it !is FurnaceBlockEntity }
        }
        return s
    }

}