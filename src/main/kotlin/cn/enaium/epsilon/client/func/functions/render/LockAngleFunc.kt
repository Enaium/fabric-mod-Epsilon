package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.events.MotioningEvent
import cn.enaium.epsilon.client.setting.FloatSetting

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
    fun on(motioningEvent: MotioningEvent) {
        motioningEvent.yaw = yaw.current
        motioningEvent.pitch = pitch.current
    }
}