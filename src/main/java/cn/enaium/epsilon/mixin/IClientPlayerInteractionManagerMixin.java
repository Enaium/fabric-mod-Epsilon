package cn.enaium.epsilon.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(ClientPlayerInteractionManager.class)
public interface IClientPlayerInteractionManagerMixin {
    @Invoker
    ActionResult invokeInteractBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult);

    @Invoker
    ActionResult invokeInteractItem(PlayerEntity player, World world, Hand hand);
}
