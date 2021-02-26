package cn.enaium.epsilon.mixin;

import cn.enaium.cf4m.CF4M;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "getAttackCooldownProgressPerTick", cancellable = true)
    public void getAttackCooldownProgressPerTick(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (CF4M.INSTANCE.module.getEnable(CF4M.INSTANCE.module.getModule("NoCooldown"))) {
            callbackInfoReturnable.setReturnValue(.1F);
        }
    }
}
