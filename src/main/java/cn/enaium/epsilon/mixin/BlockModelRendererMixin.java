package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.event.events.ShouldDrawSideEvent;
import cn.enaium.epsilon.event.events.TessellateBlockEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(BlockModelRenderer.class)
public class BlockModelRendererMixin {
    @Inject(at = {@At("HEAD")}, method = {"renderSmooth"}, cancellable = true)
    private void renderSmooth(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        TessellateBlockEvent tessellateBlockEvent = new TessellateBlockEvent(state);
        tessellateBlockEvent.call();

        if (tessellateBlockEvent.getCancelled()) {
            callbackInfoReturnable.cancel();
            return;
        }

        if (!cull)
            return;

        ShouldDrawSideEvent shouldDrawSideEvent = new ShouldDrawSideEvent(state);
        shouldDrawSideEvent.call();
        if (!Boolean.TRUE.equals(shouldDrawSideEvent.getRendered()))
            return;

        renderSmooth(world, model, state, pos, buffer, vertexConsumer, false, random, seed, overlay);
    }

    @Shadow
    public boolean renderSmooth(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay) {
        return false;
    }
}
