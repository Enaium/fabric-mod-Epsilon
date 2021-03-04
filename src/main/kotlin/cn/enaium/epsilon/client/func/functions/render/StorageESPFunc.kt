package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.IMC
import cn.enaium.epsilon.client.events.Render3DEvent
import cn.enaium.epsilon.client.settings.EnableSetting
import cn.enaium.epsilon.client.utils.BlockUtils
import cn.enaium.epsilon.client.utils.Render3DUtils
import net.minecraft.block.entity.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.chunk.BlockEntityTickInvoker
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

    @Setting("Chest")
    private val chest = EnableSetting(true)

    @Setting("TrappedChest")
    private val trappedChest = EnableSetting(true)

    @Setting("EnderChest")
    private val enderChest = EnableSetting(true)

    @Setting("ShulkerBoxChest")
    private val shulkerBoxChest = EnableSetting(true)

    @Setting("Hopper")
    private val hopper = EnableSetting(true)

    @Setting("Furnace")
    private val furnace = EnableSetting(true)

    @Setting("Dispenser")
    private val dispenser = EnableSetting(true)

    @Setting("Dropper")
    private val dropper = EnableSetting(true)

    @Setting("Barrel")
    private val barrel = EnableSetting(true)

    @Setting("BlastFurnace")
    private val blastFurnace = EnableSetting(true)

    @Setting("Smoker")
    private val smoker = EnableSetting(true)

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
            Render3DUtils.drawBox(BlockUtils.getBoundingBox(t.pos), Color.GREEN, storageBox)
        }
    }

    private fun getTargets(): ArrayList<BlockEntityTickInvoker> {
        val blocks: ArrayList<BlockEntityTickInvoker> = ArrayList()
        for (blockEntity in IMC.world.blockEntityTickers) {
            when (blockEntity) {
                is ChestBlockEntity -> if (chest.enable) blocks.add(blockEntity)
                is TrappedChestBlockEntity -> if (trappedChest.enable) blocks.add(blockEntity)
                is EnderChestBlockEntity -> if (enderChest.enable) blocks.add(blockEntity)
                is ShulkerBoxBlockEntity -> if (shulkerBoxChest.enable) blocks.add(blockEntity)
                is HopperBlockEntity -> if (hopper.enable) blocks.add(blockEntity)
                is DispenserBlockEntity -> if (dispenser.enable) blocks.add(blockEntity)
                is DropperBlockEntity -> if (dropper.enable) blocks.add(blockEntity)
                is FurnaceBlockEntity -> if (furnace.enable) blocks.add(blockEntity)
                is BarrelBlockEntity -> if (barrel.enable) blocks.add(blockEntity)
                is BlastFurnaceBlockEntity -> if (blastFurnace.enable) blocks.add(blockEntity)
                is SmokerBlockEntity -> if (smoker.enable) blocks.add(blockEntity)
            }
        }
        return blocks
    }
}