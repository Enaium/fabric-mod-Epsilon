package cn.enaium.epsilon.mixin;

import cn.enaium.cf4m.CF4M;
import cn.enaium.epsilon.client.events.KeyboardEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(Keyboard.class)
class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey")
    private void onKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (action == GLFW.GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null) {
            CF4M.module.onKey(keyCode);
            new KeyboardEvent(keyCode).call();
        }
    }
}