package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.MotionEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.FloatSetting

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class LockAngleFunc : Func("LockAngle", 0, Category.RENDER) {

    val yaw = FloatSetting(this, "Yaw", 0F, -180F, 180F)

    val pitch = FloatSetting(this, "Pitch", 0F, -90F, 90F)

    @EventAT
    fun on(motionEvent: MotionEvent) {
        motionEvent.yaw = yaw.current
        motionEvent.pitch = pitch.current
    }

}