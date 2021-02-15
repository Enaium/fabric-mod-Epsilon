package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.client.utils.BlockUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(WorldChunk.class)
public class WorldChunkMixin {

    @Inject(at = @At("RETURN"), method = "addBlockEntity")
    private void onAddBlockEntity(BlockEntity blockEntity, CallbackInfo ci) {
        BlockUtils.INSTANCE.getBlockEntities().put(blockEntity.getPos(), blockEntity);
    }

    @Inject(at = @At("RETURN"), method = "removeBlockEntity(Lnet/minecraft/util/math/BlockPos;)V")
    private void onRemoveBlockEntity(BlockPos pos, CallbackInfo ci) {
        BlockUtils.INSTANCE.getBlockEntities().remove(pos);
    }
}
