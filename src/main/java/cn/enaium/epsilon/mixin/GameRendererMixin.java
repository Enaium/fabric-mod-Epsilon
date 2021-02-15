package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.client.events.Render3DEvent;
import cn.enaium.epsilon.client.utils.Render3DUtils;
import net.minecraft.client.render.GameRenderer;
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
@Mixin(GameRenderer.class)
class GameRendererMixin {
    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", ordinal = 0), method = "renderWorld")
    private void renderWorld(float tickDelta, long limitTime, MatrixStack matrixStack, CallbackInfo callbackInfo) {
        Render3DUtils.INSTANCE.settings();
        new Render3DEvent(tickDelta, limitTime, matrixStack).call();
        Render3DUtils.INSTANCE.resets();
    }
}