package cn.enaium.epsilon.utils

import cn.enaium.epsilon.Epsilon.MC
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object BlockUtils {

    fun getState(pos: BlockPos): BlockState {
        return MC.world!!.getBlockState(pos)
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