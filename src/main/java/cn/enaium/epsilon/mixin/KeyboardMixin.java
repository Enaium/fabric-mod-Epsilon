package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.event.events.EventKeyboard;
import net.minecraft.client.Keyboard;
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
public class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
    private void onKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        new EventKeyboard(keyCode, scanCode, action, modifiers).call();
    }
}