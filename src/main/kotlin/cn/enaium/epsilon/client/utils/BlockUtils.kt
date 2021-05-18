package cn.enaium.epsilon.client.utils

import cn.enaium.epsilon.client.MC
import com.google.common.collect.Maps
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.Identifier
import net.minecraft.util.InvalidIdentifierException
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
object BlockUtils {
    fun getState(pos: BlockPos): BlockState {
        return MC.world!!.getBlockState(pos)
    }

    fun getBlock(pos: BlockPos): Block {
        return getState(pos).block
    }

    fun getId(pos: BlockPos): Int {
        return Block.getRawIdFromState(getState(pos))
    }

    fun getName(pos: BlockPos): String {
        return getName(getBlock(pos))
    }

    fun getName(block: Block): String {
        return Registry.BLOCK.getId(block).toString()
    }

    fun getBlockFromName(name: String): Block {
        return try {
            Registry.BLOCK.get(Identifier(name))
        } catch (e: InvalidIdentifierException) {
            Blocks.AIR
        }
    }

    private fun getOutlineShape(pos: BlockPos): VoxelShape {
        return getState(pos).getOutlineShape(MC.world, pos)
    }

    fun getBoundingBox(pos: BlockPos): Box {
        return getOutlineShape(pos).boundingBox.offset(pos)
    }

    fun canBeClicked(pos: BlockPos): Boolean {
        return getOutlineShape(pos) !== VoxelShapes.empty()
    }
}