package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.func.Category
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object ClickGUI : Screen(LiteralText("")) {

    var categoryPanels: ArrayList<CategoryPanel> = ArrayList()

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        for (categoryPanel in categoryPanels) {
            categoryPanel.render(matrices, mouseX, mouseY, delta)
        }
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        for (categoryPanel in categoryPanels) {
            categoryPanel.mouseClicked(mouseX, mouseY, button)
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        for (categoryPanel in categoryPanels) {
            categoryPanel.mouseReleased(mouseX, mouseY, button)
        }
        return super.mouseReleased(mouseX, mouseY, button)
    }

}