package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.Epsilon;
import cn.enaium.epsilon.event.events.Render2DEvent;
import cn.enaium.epsilon.event.events.RenderItemEntityEvent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    private void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo callbackInfo) {
        RenderItemEntityEvent event = new RenderItemEntityEvent(itemEntity, f, g, matrixStack, vertexConsumerProvider, i);
        event.call();
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }
}
