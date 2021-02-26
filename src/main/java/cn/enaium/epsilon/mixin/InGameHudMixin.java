package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.client.Epsilon;
import cn.enaium.epsilon.client.events.Render2DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
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
@Mixin(InGameHud.class)
class InGameHudMixin {
    @Inject(at = @At("HEAD"), method = "render")
    private void render(MatrixStack matrixStack, float partialTicks, CallbackInfo callbackInfo) {
        if (!MinecraftClient.getInstance().options.debugEnabled)
            new Render2DEvent(matrixStack, partialTicks).call();
    }
}