package cn.enaium.epsilon.mixin;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.event.Listener;
import cn.enaium.epsilon.client.events.MotionedEvent;
import cn.enaium.epsilon.client.events.MotioningEvent;
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
        if (CF4M.command.execCommand(message)) {
            info.cancel();
        }
    }

    /**
     * @author Enaium
     */
    @Overwrite
    private void sendMovementPackets() {
        MotioningEvent motioningEvent = new MotioningEvent(this.yaw, this.pitch, this.onGround, new Vec3d(this.getX(), this.getY(), this.getZ()));
        motioningEvent.call();
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

            float motionEventYaw = motioningEvent.getYaw();
            float motionEventPitch = motioningEvent.getPitch();
            boolean motionEventGround = motioningEvent.getGround();
            double motionEventX = motioningEvent.getVec3d().x;
            double motionEventY = motioningEvent.getVec3d().y;
            double motionEventZ = motioningEvent.getVec3d().z;

            double d = motionEventX - this.lastX;
            double e = motionEventY - this.lastBaseY;
            double f = motionEventZ - this.lastZ;
            double g = (motionEventYaw - this.lastYaw);
            double h = (motionEventPitch - this.lastPitch);
            ++this.ticksSinceLastPositionPacketSent;
            boolean bl3 = d * d + e * e + f * f > 9.0E-4D || this.ticksSinceLastPositionPacketSent >= 20;
            boolean bl4 = g != 0.0D || h != 0.0D;

            this.yaw = motionEventYaw;
            this.pitch = motionEventPitch;
            this.onGround = motionEventGround;
            if (this.hasVehicle()) {
                Vec3d vec3d = this.getVelocity();
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(vec3d.x, -999.0D, vec3d.z, this.yaw, this.pitch, this.onGround));
                bl3 = false;
            } else if (bl3 && bl4) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch, this.onGround));
            } else if (bl3) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getX(), this.getY(), this.getZ(), this.onGround));
            } else if (bl4) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(this.yaw, this.pitch, this.onGround));
            } else if (this.lastOnGround != this.onGround) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(this.onGround));
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

            this.lastOnGround = motioningEvent.getGround();
            this.autoJumpEnabled = this.client.options.autoJump;
        }
        new MotionedEvent(this.yaw, this.pitch, this.onGround, new Vec3d(this.getX(), this.getY(), this.getZ())).call();
    }
}
