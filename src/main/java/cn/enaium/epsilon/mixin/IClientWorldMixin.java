package cn.enaium.epsilon.mixin;

import net.minecraft.class_5582;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.EntityList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientWorld.class)
public interface IClientWorldMixin {
    @Accessor("entityList")
    EntityList getEntityList();
}
