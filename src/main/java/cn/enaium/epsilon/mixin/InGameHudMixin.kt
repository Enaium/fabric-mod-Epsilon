package cn.enaium.epsilon.mixin

import cn.enaium.epsilon.event.events.EventRender2D
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.util.math.MatrixStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(InGameHud::class)
class InGameHudMixin {
    @Inject(at = [At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", ordinal = 4)], method = ["render"])
    private fun render(matrixStack: MatrixStack, partialTicks: Float, ci: CallbackInfo) {
        EventRender2D(matrixStack, partialTicks).call()
    }
}