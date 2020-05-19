package cn.enaium.epsilon.utils

import cn.enaium.epsilon.Epsilon.MC
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object RotationUtils {

    fun getEyesPos(): Vec3d {
        val player: ClientPlayerEntity = MC.player!!
        return Vec3d(player.x,
                player.y + player.getEyeHeight(player.pose),
                player.z)
    }

    fun getNeededRotations(vec3d: Vec3d): Rotation {
        val eyesPos: Vec3d = getEyesPos()
        val diffX = vec3d.x - eyesPos.x
        val diffY = vec3d.y - eyesPos.y
        val diffZ = vec3d.z - eyesPos.z
        val diffXZ = sqrt(diffX * diffX + diffZ * diffZ)
        val yaw = Math.toDegrees(atan2(diffZ, diffX)).toFloat() - 90f
        val pitch = (-Math.toDegrees(atan2(diffY, diffXZ))).toFloat()
        return Rotation(yaw, pitch)
    }

    fun getAngleToLookVec(vec3d: Vec3d): Double {
        val needed: Rotation = getNeededRotations(vec3d)
        val player: ClientPlayerEntity = MC.player!!
        val currentYaw = MathHelper.wrapDegrees(player.yaw)
        val currentPitch = MathHelper.wrapDegrees(player.pitch)
        val diffYaw: Float = currentYaw - needed.yaw
        val diffPitch: Float = currentPitch - needed.pitch
        return sqrt(diffYaw * diffYaw + diffPitch * diffPitch.toDouble())
    }

    fun getDistanceBetweenAngles(vec3d: Vec3d): Double {
        val needed: Rotation = getNeededRotations(vec3d)
        val player: ClientPlayerEntity = MC.player!!
        val currentYaw = MathHelper.wrapDegrees(player.yaw)
        val currentPitch = MathHelper.wrapDegrees(player.pitch)
        val diffYaw: Float = currentYaw - needed.yaw
        val diffPitch: Float = currentPitch - needed.pitch
        var angle = abs(diffYaw - diffPitch) % 360.0
        if (angle > 180.0f) {
            angle = 360.0f - angle
        }
        return angle
    }

    class Rotation(yaw: Float, pitch: Float) {
        val yaw: Float = MathHelper.wrapDegrees(yaw)
        val pitch: Float = MathHelper.wrapDegrees(pitch)
    }
}