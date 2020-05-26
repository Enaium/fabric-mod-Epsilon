package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.RenderBlockEntityEvent
import cn.enaium.epsilon.event.events.ShouldDrawSideEvent
import cn.enaium.epsilon.event.events.TessellateBlockEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.BlockListSetting
import cn.enaium.epsilon.utils.BlockUtils
import net.minecraft.block.Block
import org.lwjgl.glfw.GLFW
import java.util.*

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class XrayFunc : Func("Xray", GLFW.GLFW_KEY_X, Category.RENDER) {

    private val blocks = BlockListSetting(this, "NoXray", arrayListOf("Ores", "",
            "minecraft:anvil", "minecraft:beacon", "minecraft:bone_block",
            "minecraft:bookshelf", "minecraft:brewing_stand",
            "minecraft:chain_command_block", "minecraft:chest", "minecraft:clay",
            "minecraft:coal_block", "minecraft:coal_ore", "minecraft:command_block",
            "minecraft:crafting_table", "minecraft:diamond_block",
            "minecraft:diamond_ore", "minecraft:dispenser", "minecraft:dropper",
            "minecraft:emerald_block", "minecraft:emerald_ore",
            "minecraft:enchanting_table", "minecraft:end_portal",
            "minecraft:end_portal_frame", "minecraft:ender_chest",
            "minecraft:furnace", "minecraft:glowstone", "minecraft:gold_block",
            "minecraft:gold_ore", "minecraft:hopper", "minecraft:iron_block",
            "minecraft:iron_ore", "minecraft:ladder", "minecraft:lapis_block",
            "minecraft:lapis_ore", "minecraft:lava", "minecraft:mossy_cobblestone",
            "minecraft:nether_portal", "minecraft:nether_quartz_ore",
            "minecraft:redstone_block", "minecraft:redstone_ore",
            "minecraft:repeating_command_block", "minecraft:spawner",
            "minecraft:tnt", "minecraft:torch", "minecraft:trapped_chest",
            "minecraft:water", "minecraft:ancient_debris"))

    override fun onEnable() {
        super.onEnable()
        MC.worldRenderer.reload()
    }

    override fun onDisable() {
        super.onDisable()
        MC.worldRenderer.reload()
    }

    @EventAT
    fun shouldDrawSideEvent(shouldDrawSideEvent: ShouldDrawSideEvent) {
        shouldDrawSideEvent.rendered = isXray(shouldDrawSideEvent.blockState.block)
    }

    @EventAT
    fun tessellateBlockEvent(tessellateBlockEvent: TessellateBlockEvent) {
        if (!isXray(tessellateBlockEvent.blockState.block)) {
            tessellateBlockEvent.cancel()
        }
    }

    @EventAT
    fun renderBlockEntityEvent(renderBlockEntityEvent: RenderBlockEntityEvent) {
        if (!isXray(BlockUtils.getBlock(renderBlockEntityEvent.blockEntity.pos))) {
            renderBlockEntityEvent.cancel()
        }
    }

    private fun isXray(block: Block): Boolean {
        return Collections.binarySearch(blocks.blockList, BlockUtils.getName(block)) >= 0
    }
}