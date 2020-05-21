package cn.enaium.epsilon.mixin

import cn.enaium.epsilon.event.events.MouseScrollEvent
import net.minecraft.client.Mouse
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(Mouse::class)
class MouseMixin {
    @Inject(at = [At("HEAD")], method = ["onMouseScroll"])
    private fun onKey(windowHandle: Long, up: Double, down: Double, callbackInfo: CallbackInfo) {
        MouseScrollEvent(windowHandle, up, down).call()
    }
}