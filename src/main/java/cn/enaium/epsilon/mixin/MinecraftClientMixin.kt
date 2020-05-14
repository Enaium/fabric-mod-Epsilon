package cn.enaium.epsilon.mixin

import cn.enaium.epsilon.Epsilon.AUTHOR
import cn.enaium.epsilon.Epsilon.GAME
import cn.enaium.epsilon.Epsilon.NAME
import cn.enaium.epsilon.Epsilon.VERSION
import cn.enaium.epsilon.Epsilon.run
import cn.enaium.epsilon.Epsilon.stop
import cn.enaium.epsilon.imixin.IMinecraftClient
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.TitleScreen
import net.minecraft.client.util.Window
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(MinecraftClient::class)
class MinecraftClientMixin : IMinecraftClient {
    @Shadow
    lateinit var window: Window
    override fun window(): Window {
        return window
    }

    @Inject(at = [At("HEAD")], method = ["run()V"])
    private fun run(ci: CallbackInfo) {
        run()
    }

    @Inject(at = [At("HEAD")], method = ["stop()V"])
    private fun stop(ci: CallbackInfo) {
        stop()
    }

//    @Shadow
//    var currentScreen: Screen? = null
//
//    @Inject(at = [At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", shift = At.Shift.AFTER)], method = ["openScreen"])
//    private fun openScreen(screen: Screen, info: CallbackInfo) {
//        if (currentScreen is TitleScreen) {
//        }
//    }

    @Inject(at = [At("RETURN")], method = ["updateWindowTitle()V"])
    private fun updateWindowTitle(ci: CallbackInfo) {
        window.setTitle("$NAME | $VERSION | $GAME | $AUTHOR")
    }
}