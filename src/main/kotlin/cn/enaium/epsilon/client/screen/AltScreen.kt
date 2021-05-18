package cn.enaium.epsilon.client.screen

import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.IMC
import cn.enaium.epsilon.client.utils.Render2DUtils
import com.mojang.authlib.Agent
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.FatalErrorScreen
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.ButtonWidget.PressAction
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.render.*
import net.minecraft.client.util.Session
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.awt.Color
import java.net.Proxy


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class AltScreen : Screen(LiteralText("")) {

    private lateinit var usernameField: TextFieldWidget
    private lateinit var passwordField: TextFieldWidget

    override fun init() {
        super.init()
        usernameField = TextFieldWidget(textRenderer, width / 2 - 250 / 2, 5, 250, 20, LiteralText(""))
        passwordField = TextFieldWidget(textRenderer, width / 2 - 250 / 2, 35, 250, 20, LiteralText(""))
        addButton(ButtonWidget(width / 2 - 250 / 2, 40 * 3, 250, 20, LiteralText("Login")) {
            login()
        })
        addButton(ButtonWidget(width / 2 - 250 / 2, 40 * 3 + 25 * 2, 250, 20, LiteralText("Back")) {
            MC.openScreen(null)
        })
        children.addAll(arrayOf(usernameField, passwordField))
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        val glsl = Render2DUtils.GLSL("/assets/epsilon/shaders/noise")
        GL20.glUseProgram(glsl.program)
        GL20.glUniform2i(GL20.glGetUniformLocation(glsl.program, "resolution"), width, height)
        GL20.glUseProgram(0)
        drawCenteredString(matrixStack, textRenderer, "Username:", width / 2 - 250 / 2 - 50, 5, -1)
        drawCenteredString(matrixStack, textRenderer, "Password:", width / 2 - 250 / 2 - 50, 35, -1)
        usernameField.render(matrixStack, mouseX, mouseY, delta)
        passwordField.render(matrixStack, mouseX, mouseY, delta)

        super.render(matrixStack, mouseX, mouseY, delta)
    }

    private fun login() {
        try {
            IMC.mc.session = createSession(usernameField.text, passwordField.text, Proxy.NO_PROXY)
            MC.openScreen(null)
        } catch (e: Exception) {
            MC.openScreen(FatalErrorScreen(LiteralText("Login Error"), LiteralText(e.message)))
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        usernameField.mouseClicked(mouseX, mouseY, button)
        passwordField.mouseClicked(mouseX, mouseY, button)
        return super.mouseClicked(mouseX, mouseY, button)
    }

    private fun createSession(username: String, password: String, proxy: Proxy): Session {
        val service = YggdrasilAuthenticationService(proxy, "")
        val auth = service
            .createUserAuthentication(Agent.MINECRAFT) as YggdrasilUserAuthentication
        auth.setUsername(username)
        auth.setPassword(password)
        auth.logIn()
        return Session(
            auth.selectedProfile.name, auth.selectedProfile.id.toString(),
            auth.authenticatedToken, "mojang"
        )
    }


}