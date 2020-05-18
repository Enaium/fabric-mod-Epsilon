package cn.enaium.epsilon.func.funcs.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.Render3DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT
import cn.enaium.epsilon.setting.SettingAT
import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.utils.BlockUtils
import cn.enaium.epsilon.utils.Render3DUtils
import net.minecraft.block.entity.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class StorageESP : Func("StorageESP", 0, Category.RENDER) {

    @SettingAT
    private val chest = EnableSetting(this, "Chest", true)

    @SettingAT
    private val trappedChest = EnableSetting(this, "TrappedChest", true)

    @SettingAT
    private val enderChest = EnableSetting(this, "EnderChest", true)

    @SettingAT
    private val shulkerBoxChest = EnableSetting(this, "ShulkerBoxChest", true)

    @SettingAT
    private val hopper = EnableSetting(this, "Hopper", true)

    @SettingAT
    private val furnace = EnableSetting(this, "Furnace", true)

    @SettingAT
    private val dispenser = EnableSetting(this, "Dispenser", true)

    @SettingAT
    private val dropper = EnableSetting(this, "Dropper", true)

    @SettingAT
    private val barrel = EnableSetting(this, "Barrel", true)

    @SettingAT
    private val blastFurnace = EnableSetting(this, "BlastFurnace", true)

    @SettingAT
    private val smoker = EnableSetting(this, "Smoker", true)

    override fun onEnable() {
        super.onEnable()
        GL11.glNewList(1, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    override fun onDisable() {
        super.onDisable()
        GL11.glDeleteLists(1, 1)
    }

    @EventAT
    fun onRender(render3DEvent: Render3DEvent) {
        Render3DUtils.applyRenderOffset()
        for (t in getTargets()) {
            Render3DUtils.drawOutlinedBox(BlockUtils.getBoundingBox(t.pos), Color.DARK_GRAY)
        }
    }

    private fun getTargets(): ArrayList<BlockEntity> {
        val blocks: ArrayList<BlockEntity> = ArrayList()
        for (be in Epsilon.MC.world!!.blockEntities) {
            when (be) {
                is ChestBlockEntity -> if (chest.enable) blocks.add(be)
                is TrappedChestBlockEntity -> if (trappedChest.enable) blocks.add(be)
                is EnderChestBlockEntity -> if (enderChest.enable) blocks.add(be)
                is ShulkerBoxBlockEntity -> if (shulkerBoxChest.enable) blocks.add(be)
                is HopperBlockEntity -> if (hopper.enable) blocks.add(be)
                is DispenserBlockEntity -> if (dispenser.enable) blocks.add(be)
                is DropperBlockEntity -> if (dropper.enable) blocks.add(be)
                is FurnaceBlockEntity -> if (furnace.enable) blocks.add(be)
                is BarrelBlockEntity -> if (barrel.enable) blocks.add(be)
                is BlastFurnaceBlockEntity -> if (blastFurnace.enable) blocks.add(be)
                is SmokerBlockEntity -> if (smoker.enable) blocks.add(be)
            }
        }
        return blocks
    }

}