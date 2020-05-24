package cn.enaium.epsilon.mixin;

import cn.enaium.epsilon.Epsilon;
import cn.enaium.epsilon.event.Event;
import cn.enaium.epsilon.event.events.MotionEvent;
import cn.enaium.epsilon.event.events.UpdateEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info) {
        if (Epsilon.commandManager.processCommand(message)) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void preTick(CallbackInfo ci) {
        new UpdateEvent().call();
    }

    @Inject(at = @At("HEAD"), method = "sendMovementPackets()V")
    private void onSendMovementPacketsHEAD(CallbackInfo ci) {
        new MotionEvent(Event.Type.PRE).call();
    }

    @Inject(at = @At("TAIL"), method = "sendMovementPackets()V")
    private void onSendMovementPacketsTAIL(CallbackInfo ci) {
        new MotionEvent(Event.Type.POST).call();
    }
}
