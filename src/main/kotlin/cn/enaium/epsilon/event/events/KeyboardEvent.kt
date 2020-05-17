package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class KeyboardEvent(val keyCode: Int, val scanCode: Int, val action: Int, val modifiers: Int) : Event(Type.PRE)