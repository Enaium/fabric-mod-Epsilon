package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.RenderBlockEntityEvent
import cn.enaium.epsilon.client.events.ShouldDrawSideEvent
import cn.enaium.epsilon.client.events.TessellateBlockEvent
import cn.enaium.epsilon.client.settings.BlockListSetting
import cn.enaium.epsilon.client.utils.BlockUtils
import net.minecraft.block.Block
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Xray", key = GLFW.GLFW_KEY_X, category = Category.RENDER)
class XrayFunc {

    @Setting("NoXray")
    private val blocks = BlockListSetting(arrayListOf(
            "Ores", "",
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
            "minecraft:water", "minecraft:ancient_debris"
        )
    )

    @Enable
    fun onEnable() {
        MC.worldRenderer.reload()
    }

    @Disable
    fun onDisable() {
        MC.worldRenderer.reload()
    }

    @Event
    fun shouldDrawSideEvent(shouldDrawSideEvent: ShouldDrawSideEvent) {
        shouldDrawSideEvent.rendered = isXray(shouldDrawSideEvent.blockState.block)
    }

    @Event
    fun tessellateBlockEvent(tessellateBlockEvent: TessellateBlockEvent) {
        if (!isXray(tessellateBlockEvent.blockState.block)) {
            tessellateBlockEvent.cancel = true;
        }
    }

    @Event
    fun renderBlockEntityEvent(renderBlockEntityEvent: RenderBlockEntityEvent) {
        if (!isXray(BlockUtils.getBlock(renderBlockEntityEvent.blockEntity.pos))) {
            renderBlockEntityEvent.cancel = true;
        }
    }

    private fun isXray(block: Block): Boolean {
        return blocks.blockList.contains(BlockUtils.getName(block))
    }
}