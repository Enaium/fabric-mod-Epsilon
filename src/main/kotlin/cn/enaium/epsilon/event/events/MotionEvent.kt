package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.util.math.Vec3d

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class MotionEvent(type: Type, var yaw: Float, var pitch: Float, var ground: Boolean, var vec3d: Vec3d) : Event(type)