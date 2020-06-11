package cn.enaium.epsilon.ui.elements

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.block.Blocks
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class Image : Element {

    private var image: Identifier? = null
    private var itemStack: ItemStack? = null

    constructor(x: Int, y: Int, width: Int, height: Int, image: Identifier) : super(x, y, width, height) {
        this.image = image
    }

    constructor(x: Int, y: Int, itemStack: ItemStack) : super(x, y, 16, 16) {
        this.itemStack = itemStack
    }

    constructor(x: Int, y: Int, width: Int, height: Int, image: String) : this(
        x,
        y,
        width,
        height,
        Identifier("epsilon", image)
    )

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.hovered = Render2DUtils.isHovered(mouseX, mouseY, x, y, width, height)
        if (image != null) {
            Render2DUtils.drawImage(image!!, x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        }

        if (itemStack != null) {
            MC.itemRenderer.renderGuiItemIcon(itemStack!!, x, y)
        }
    }
}