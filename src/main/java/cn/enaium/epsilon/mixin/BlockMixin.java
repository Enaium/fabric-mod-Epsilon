package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.event.events.ShouldDrawSideEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = {@At("HEAD")}, method = {"shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"}, cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView blockView, BlockPos blockPos, Direction side, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        ShouldDrawSideEvent event = new ShouldDrawSideEvent(state);
        event.call();

        if (event.getRendered() != null)
            callbackInfoReturnable.setReturnValue(event.getRendered());
    }
}
