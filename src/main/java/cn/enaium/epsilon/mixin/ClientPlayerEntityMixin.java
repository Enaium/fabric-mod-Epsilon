package cn.enaium.epsilon.mixin;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.EventBase;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.epsilon.client.events.MotionEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow
    private boolean autoJumpEnabled;
    @Shadow
    private boolean lastOnGround;
    @Shadow
    private boolean lastSneaking;
    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;
    @Shadow
    private boolean lastSprinting;
    @Shadow
    private float lastPitch;
    @Shadow
    private float lastYaw;
    @Shadow
    private double lastX;
    @Shadow
    private double lastBaseY;
    @Shadow
    private double lastZ;
    @Shadow
    private int ticksSinceLastPositionPacketSent;

    @Shadow
    protected abstract boolean isCamera();

    @Shadow
    @Final
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info) {
        if (CF4M.INSTANCE.command.isCommand(message)) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo ci) {
        new UpdateEvent().call();
    }

    @Inject(at = @At("RETURN"), method = "tick()V")
    private void preTick(CallbackInfo ci) {
        new MotionEvent(EventBase.Type.PRE, this.yaw, this.pitch, this.onGround, new Vec3d(this.getX(), this.getY(), this.getZ())).call();
    }

    /**
     * @author Enaium
     */
    @Overwrite
    private void sendMovementPackets() {
        MotionEvent motionEvent = new MotionEvent(EventBase.Type.POST, this.yaw, this.pitch, this.onGround, new Vec3d(this.getX(), this.getY(), this.getZ()));
        motionEvent.call();
        boolean bl = this.isSprinting();
        if (bl != this.lastSprinting) {
            ClientCommandC2SPacket.Mode mode = bl ? ClientCommandC2SPacket.Mode.START_SPRINTING : ClientCommandC2SPacket.Mode.STOP_SPRINTING;
            this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, mode));
            this.lastSprinting = bl;
        }

        boolean bl2 = this.isSneaking();
        if (bl2 != this.lastSneaking) {
            ClientCommandC2SPacket.Mode mode2 = bl2 ? ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY : ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY;
            this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, mode2));
            this.lastSneaking = bl2;
        }
        if (this.isCamera()) {

            float motionEventYaw = motionEvent.getYaw();
            float motionEventPitch = motionEvent.getPitch();
            boolean motionEventGround = motionEvent.getGround();
            double motionEventX = motionEvent.getVec3d().x;
            double motionEventY = motionEvent.getVec3d().y;
            double motionEventZ = motionEvent.getVec3d().z;

            double d = motionEventX - this.lastX;
            double e = motionEventY - this.lastBaseY;
            double f = motionEventZ - this.lastZ;
            double g = (motionEventZ - this.lastYaw);
            double h = (motionEventPitch - this.lastPitch);
            ++this.ticksSinceLastPositionPacketSent;
            boolean bl3 = d * d + e * e + f * f > 9.0E-4D || this.ticksSinceLastPositionPacketSent >= 20;
            boolean bl4 = g != 0.0D || h != 0.0D;

            this.yaw = motionEventYaw;
            this.pitch = motionEventPitch;
            this.onGround = motionEventGround;
            if (this.hasVehicle()) {
                Vec3d vec3d = this.getVelocity();
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(vec3d.x, -999.0D, vec3d.z, motionEventYaw, motionEventPitch, motionEventGround));
                bl3 = false;
            } else if (bl3 && bl4) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(motionEventX, motionEventY, motionEventZ, motionEventYaw, motionEventPitch, motionEventGround));
            } else if (bl3) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(motionEventX, motionEventY, motionEventZ, motionEventGround));
            } else if (bl4) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookOnly(motionEventYaw, motionEventPitch, motionEventGround));
            } else if (this.lastOnGround != motionEventGround) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket(motionEventGround));
            }

            if (bl3) {
                this.lastX = motionEventX;
                this.lastBaseY = motionEventY;
                this.lastZ = motionEventZ;
                this.ticksSinceLastPositionPacketSent = 0;
            }

            if (bl4) {
                this.lastYaw = motionEventYaw;
                this.lastPitch = this.pitch;
            }

            this.lastOnGround = motionEvent.getGround();
            this.autoJumpEnabled = this.client.options.autoJump;
        }
    }
}
