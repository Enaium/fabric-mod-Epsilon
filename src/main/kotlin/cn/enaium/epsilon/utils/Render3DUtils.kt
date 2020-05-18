package cn.enaium.epsilon.utils

import net.minecraft.client.render.Camera
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.entity.Entity
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object Render3DUtils {
    fun applyRenderOffset() {
        applyCameraRotationOnly()
        val camPos = getCameraPos()
        GL11.glTranslated(-camPos.x, -camPos.y, -camPos.z)
    }

    fun applyCameraRotationOnly() {
        val camera: Camera = BlockEntityRenderDispatcher.INSTANCE.camera
        GL11.glRotated(MathHelper.wrapDegrees(camera.pitch).toDouble(), 1.0, 0.0, 0.0)
        GL11.glRotated(MathHelper.wrapDegrees(camera.yaw + 180.0), 0.0, 1.0, 0.0)
    }

    fun getCameraPos(): Vec3d {
        return BlockEntityRenderDispatcher.INSTANCE.camera.pos
    }

    fun drawOutlined(bb: Box) {
        GL11.glBegin(GL11.GL_LINES)
        GL11.glVertex3d(bb.x1, bb.y1, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y1, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y1, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y1, bb.z2)
        GL11.glVertex3d(bb.x2, bb.y1, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y1, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y1, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y1, bb.z1)
        GL11.glVertex3d(bb.x1, bb.y1, bb.z1)
        GL11.glVertex3d(bb.x1, bb.y2, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y1, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y2, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y1, bb.z2)
        GL11.glVertex3d(bb.x2, bb.y2, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y1, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y2, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y2, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y2, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y2, bb.z1)
        GL11.glVertex3d(bb.x2, bb.y2, bb.z2)
        GL11.glVertex3d(bb.x2, bb.y2, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y2, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y2, bb.z2)
        GL11.glVertex3d(bb.x1, bb.y2, bb.z1)
        GL11.glEnd()
    }

    fun drawOutlinedBox(entity: Entity, extraSize: Double, tickDelta: Float, color: Color) {
        GL11.glPushMatrix()
        GL11.glTranslated(
                entity.prevX + (entity.x - entity.prevX) * tickDelta,
                entity.prevY + (entity.y - entity.prevY) * tickDelta,
                entity.prevZ + (entity.z - entity.prevZ) * tickDelta
        )
        GL11.glScaled(entity.width + extraSize,
                entity.height + extraSize,
                entity.width + extraSize
        )
        GL11.glColor4f(color.red.toFloat(), color.green.toFloat(), color.blue.toFloat(), color.alpha.toFloat())
        GL11.glCallList(1)
        GL11.glPopMatrix()
    }

    fun drawOutlinedBox(box: Box, color: Color) {
        GL11.glPushMatrix()
        GL11.glTranslated(box.x1, box.y1, box.z1)
        GL11.glScaled(box.x2 - box.x1, box.y2 - box.y1, box.z2 - box.z1)
        GL11.glColor4f(color.red.toFloat(), color.green.toFloat(), color.blue.toFloat(), color.alpha.toFloat())
        GL11.glCallList(1)
        GL11.glPopMatrix()
    }

    fun settings() {
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glLineWidth(2f)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glDisable(GL11.GL_LIGHTING)
    }

    fun resets() {
        GL11.glColor4f(1f, 1f, 1f, 1f)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glDisable(GL11.GL_LINE_SMOOTH)
    }
}