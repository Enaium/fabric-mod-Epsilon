package cn.enaium.epsilon.mixin;

import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(World.class)
public interface IWorld {
    @Accessor("blockEntityTickers")
    List<BlockEntityTickInvoker> getBlockEntityTickers();
}
