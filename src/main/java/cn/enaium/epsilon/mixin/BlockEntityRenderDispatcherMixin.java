package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.event.events.RenderBlockEntityEvent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", cancellable = true)
    private <E extends BlockEntity> void render(E blockEntity, float tickDelta, MatrixStack matrix, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo) {
        RenderBlockEntityEvent event = new RenderBlockEntityEvent(blockEntity);
        event.call();
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }

}
