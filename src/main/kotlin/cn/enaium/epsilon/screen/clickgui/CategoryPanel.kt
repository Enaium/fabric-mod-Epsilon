package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color
import java.util.*


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class CategoryPanel(var category: Category, var x: Double, var y: Double) {

    private var hovering = false
    private var dragging = false
    private var tempX = 0.0
    private var tempY = 0.0
    private var width = 0.0
    private var height = 0.0

    private var funcPanels: ArrayList<FuncPanel> = ArrayList()
    var displayFuncPanel = false

    init {
        width = getWidestCategory() + 50
        height = 20.0
        for (m in Epsilon.funcManager.getFuncForCategory(category)) {
            this.funcPanels.add(FuncPanel(m, width, height))
        }
    }

    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        if (dragging) {
            x = tempX + mouseX
            y = tempY + mouseY
        }
        hovering = Render2DUtils.isHovered(mouseX.toDouble(), mouseY.toDouble(), x, y, width, height)
        Render2DUtils.drawRect(matrices, x, y, x + width, y + height, if (hovering) Color.BLUE.rgb else Color.RED.rgb)
        FontUtils.drawHVCenteredString(matrices, category.name, (x + (x + width)) / 2, (y + (y + height)) / 2, 0xFFFFFF)

        if (displayFuncPanel) {
            var funcY = y + height
            for (funcPanel in funcPanels) {
                funcPanel.render(matrices, mouseX, mouseY, delta, x, funcY)
                funcY += height
            }
        }
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering) {
            if (button == 0) {
                dragging = true
                tempX = x - mouseX
                tempY = y - mouseY
            } else if (button == 1) {
                displayFuncPanel = !displayFuncPanel
            }
        }
        for (funcPanel in funcPanels) {
            funcPanel.mouseClicked(mouseX, mouseY, button)
        }
    }

    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering && button == 0) {
            dragging = false
        }
    }


    private fun getWidestCategory(): Double {
        var width = 0.0
        for (c in Category.values()) {
            val name: String = c.name
            val cWidth = FontUtils.getWidth(
                name.substring(0, 1).toUpperCase() + name.substring(1, name.length).toLowerCase()
            ).toDouble()
            if (cWidth > width) {
                width = cWidth
            }
        }
        return width
    }
}