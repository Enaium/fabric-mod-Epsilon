package cn.enaium.epsilon.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(GameRenderer.class)
public interface IGameRendererMixin {

    @Accessor("shadersEnabled")
    boolean isShadersEnabled();

    @Invoker
    void invokeLoadShader(Identifier identifier);
}
