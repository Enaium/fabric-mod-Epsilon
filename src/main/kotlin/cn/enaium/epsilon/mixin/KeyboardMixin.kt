package cn.enaium.epsilon.mixin

import cn.enaium.epsilon.event.events.EventKeyboard
import net.minecraft.client.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(Keyboard::class)
class KeyboardMixin {
    @Inject(at = [At("HEAD")], method = ["onKey"])
    private fun onKey(windowHandle: Long, keyCode: Int, scanCode: Int, action: Int, modifiers: Int, callbackInfo: CallbackInfo) {
        EventKeyboard(keyCode, scanCode, action, modifiers).call()
    }
}