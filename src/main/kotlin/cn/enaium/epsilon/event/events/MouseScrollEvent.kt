package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class MouseScrollEvent(val windowHandle: Long, val up: Double, val down: Double) : Event(Type.PRE)