package cn.enaium.epsilon.utils;

import cn.enaium.epsilon.Epsilon;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.Rotation3;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class Render2DUtils {

    public static int getScaledWidth() {
        return MinecraftClient.getInstance().getWindow().getScaledWidth();
    }

    public static int getScaledHeight() {
        return MinecraftClient.getInstance().getWindow().getScaledHeight();
    }


    public static void drawRect(int x1, int y1, int x2, int y2, int color) {
        DrawableHelper.fill(x1, y1, x2, y2, color);
    }

    public static void drawRect(double x1, double y1, double x2, double y2, int color) {
        fill(Rotation3.identity().getMatrix(), x1, y1, x2, y2, color);
    }

    public static void drawRectWH(int x, int y, int width, int height, int color) {
        DrawableHelper.fill(x, y, x + width, y + height, color);
    }

    public static void drawRectWH(double x, double y, double width, double height, int color) {
        fill(Rotation3.identity().getMatrix(), x, y, x + width, y + height, color);
    }

    public static void drawImage(String image, double x, double y, double width, double height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Epsilon.INSTANCE.getMC().getTextureManager().bindTexture(new Identifier("epsilon", image));
        blit(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawHorizontalLine(int i, int j, int k, int l) {
        if (j < i) {
            int m = i;
            i = j;
            j = m;
        }

        drawRect(i, k, j + 1, k + 1, l);
    }

    public static void drawVerticalLine(int i, int j, int k, int l) {
        if (k < j) {
            int m = j;
            j = k;
            k = m;
        }

        drawRect(i, j + 1, i + 1, k, l);
    }

    private static void fill(Matrix4f matrix4f, double x1, double y1, double x2, double y2, int color) {
        double j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, (float) x1, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix4f, (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix4f, (float) x2, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix4f, (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private static void blit(double x, double y, float u, float v, double width, double height, double texWidth, double texHeight) {
        blit(x, y, width, height, u, v, width, height, texWidth, texHeight);
    }

    private static void blit(double x, double y, double width, double height, float u, float v, double uWidth, double vHeight, double texWidth, double texHeight) {
        innerBlit(x, x + width, y, y + height, 0, uWidth, vHeight, u, v, texWidth, texHeight);
    }

    private static void innerBlit(double xStart, double xEnd, double yStart, double yEnd, double z, double width, double height, float u, float v, double texWidth, double texHeight) {
        innerBlit(xStart, xEnd, yStart, yEnd, z, (u + 0.0F) / (float) texWidth, (u + (float) width) / (float) texWidth, (v + 0.0F) / (float) texHeight, (v + (float) height) / (float) texHeight);
    }

    private static void innerBlit(double xStart, double xEnd, double yStart, double yEnd, double z, float uStart, float uEnd, float vStart, float vEnd) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(xStart, yEnd, z).texture(uStart, vEnd).next();
        bufferBuilder.vertex(xEnd, yEnd, z).texture(uEnd, vEnd).next();
        bufferBuilder.vertex(xEnd, yStart, z).texture(uEnd, vStart).next();
        bufferBuilder.vertex(xStart, yStart, z).texture(uStart, vStart).next();
        bufferBuilder.end();
        RenderSystem.enableAlphaTest();
        BufferRenderer.draw(bufferBuilder);
    }

    public static void setColor(Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public static void setColor(int rgba) {
        int r = rgba & 0xFF;
        int g = rgba >> 8 & 0xFF;
        int b = rgba >> 16 & 0xFF;
        int a = rgba >> 24 & 0xFF;
        GL11.glColor4b((byte) r, (byte) g, (byte) b, (byte) a);
    }

    public static int toRGBA(Color c) {
        return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }

    public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }

    public static boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }
}