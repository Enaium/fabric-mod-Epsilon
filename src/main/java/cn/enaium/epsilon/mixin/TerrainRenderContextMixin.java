package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.client.events.TessellateBlockEvent;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {
    @Inject(at = @At("HEAD"), method = "tesselateBlock", cancellable = true, remap = false)
    private void tessellateBlock(BlockState blockState, BlockPos blockPos, final BakedModel model, MatrixStack matrixStack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        TessellateBlockEvent event = new TessellateBlockEvent(blockState);
        event.call();

        if (event.getCancel())
            callbackInfoReturnable.cancel();
    }
}
