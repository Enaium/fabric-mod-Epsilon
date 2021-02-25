package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.events.MotionEvent
import cn.enaium.epsilon.client.settings.FloatSetting

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("LockAngle", category = Category.RENDER)
class LockAngleFunc {

    @Setting("Yaw")
    private val yaw = FloatSetting(0F, -180F, 180F)

    @Setting("Pitch")
    private val pitch = FloatSetting(0F, -90F, 90F)

    @Event
    fun on(motionEvent: MotionEvent) {
        motionEvent.yaw = yaw.current
        motionEvent.pitch = pitch.current
    }
}