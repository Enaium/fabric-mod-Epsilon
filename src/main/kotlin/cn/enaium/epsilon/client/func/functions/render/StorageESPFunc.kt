package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.events.Render3DEvent
import cn.enaium.epsilon.client.utils.BlockUtils
import cn.enaium.epsilon.client.utils.Render3DUtils
import net.minecraft.block.entity.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("StorageESP", category = Category.RENDER)
class StorageESPFunc {
    private val chest = EnableSetting(this, "Chest", "Chest", true)

    private val trappedChest = EnableSetting(this, "TrappedChest", "Render TrappedChest", true)

    private val enderChest = EnableSetting(this, "EnderChest", "Render EnderChest", true)

    private val shulkerBoxChest = EnableSetting(this, "ShulkerBoxChest", "Render ShulkerBoxChest", true)

    private val hopper = EnableSetting(this, "Hopper", "Render Hopper", true)

    private val furnace = EnableSetting(this, "Furnace", "Render Furnace", true)

    private val dispenser = EnableSetting(this, "Dispenser", "Render Dispenser", true)

    private val dropper = EnableSetting(this, "Dropper", "Render Dropper", true)

    private val barrel = EnableSetting(this, "Barrel", "Render Barrel", true)

    private val blastFurnace = EnableSetting(this, "BlastFurnace", "Render BlastFurnace", true)

    private val smoker = EnableSetting(this, "Smoker", "Render Smoker", true)

    private var storageBox = 0

    @Enable
    fun onEnable() {
        storageBox = GL11.glGenLists(1)
        GL11.glNewList(storageBox, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    @Disable
    fun onDisable() {
        GL11.glDeleteLists(storageBox, 1)
        storageBox = 0
    }

    @Event
    fun onRender(render3DEvent: Render3DEvent) {
        for (t in getTargets()) {
            Render3DUtils.drawBox(BlockUtils.getBoundingBox(t), Color.GREEN, storageBox)
        }
    }

    private fun getTargets(): ArrayList<BlockPos> {
        val blocks: ArrayList<BlockPos> = ArrayList()
        for (blockEntity in BlockUtils.blockEntities.values) {
            when (blockEntity) {
                is ChestBlockEntity -> if (chest.enable) blocks.add(blockEntity.pos)
                is TrappedChestBlockEntity -> if (trappedChest.enable) blocks.add(blockEntity.pos)
                is EnderChestBlockEntity -> if (enderChest.enable) blocks.add(blockEntity.pos)
                is ShulkerBoxBlockEntity -> if (shulkerBoxChest.enable) blocks.add(blockEntity.pos)
                is HopperBlockEntity -> if (hopper.enable) blocks.add(blockEntity.pos)
                is DispenserBlockEntity -> if (dispenser.enable) blocks.add(blockEntity.pos)
                is DropperBlockEntity -> if (dropper.enable) blocks.add(blockEntity.pos)
                is FurnaceBlockEntity -> if (furnace.enable) blocks.add(blockEntity.pos)
                is BarrelBlockEntity -> if (barrel.enable) blocks.add(blockEntity.pos)
                is BlastFurnaceBlockEntity -> if (blastFurnace.enable) blocks.add(blockEntity.pos)
                is SmokerBlockEntity -> if (smoker.enable) blocks.add(blockEntity.pos)
            }
        }
        return blocks
    }
}