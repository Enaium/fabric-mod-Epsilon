package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.settings.FloatSetting
import cn.enaium.epsilon.client.events.MotionEvent

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("LockAngle", category = Category.RENDER)
class LockAngleFunc {

    private val yaw = FloatSetting(this, "Yaw", "lock yaw", 0F, -180F, 180F)

    private val pitch = FloatSetting(this, "Pitch", "lock pitch", 0F, -90F, 90F)

    @Event
    fun on(motionEvent: MotionEvent) {
        motionEvent.yaw = yaw.current
        motionEvent.pitch = pitch.current
    }
}