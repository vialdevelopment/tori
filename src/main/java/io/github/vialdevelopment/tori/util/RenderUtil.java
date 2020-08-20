package io.github.vialdevelopment.tori.util;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

/**
 * I hated every moment of writing this
 */
public class RenderUtil {

    private static final Tessellator tessellator = Tessellator.getInstance();

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawLine(Vec3d startPos, Vec3d endPos, Color color) {
        final Vec3d interpolatedStart = RenderUtil.interpolatePos(startPos);

        final Vec3d interpolatedEnd = RenderUtil.interpolatePos(endPos);

        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, VertexFormats.POSITION_COLOR);

        buffer.vertex(interpolatedStart.getX(), interpolatedStart.getY(), interpolatedStart.getZ()).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        buffer.vertex(interpolatedEnd.getX(), interpolatedEnd.getY(), interpolatedEnd.getZ()).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();

        // the draw func runs the end func in the buffer builder
        tessellator.draw();

    }

    public static void drawBox(Box box, Color color) {

        final int r = color.getRed();
        final int g = color.getGreen();
        final int b = color.getBlue();
        final int a = color.getAlpha();

        final Box interpolatedBox = RenderUtil.interpolateBox(box);

        final BufferBuilder buffer = tessellator.getBuffer();

        // this is pain
        buffer.begin(3, VertexFormats.POSITION_COLOR);
        buffer.vertex(interpolatedBox.minX, interpolatedBox.minY, interpolatedBox.minZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.minX, interpolatedBox.minY, interpolatedBox.maxZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.minY, interpolatedBox.maxZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.minY, interpolatedBox.minZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.minX, interpolatedBox.minY, interpolatedBox.minZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.minX, interpolatedBox.maxY, interpolatedBox.minZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.maxY, interpolatedBox.minZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.maxY, interpolatedBox.maxZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.minX, interpolatedBox.maxY, interpolatedBox.maxZ).color(r, g, b, a).next();
        buffer.vertex(interpolatedBox.minX, interpolatedBox.maxY, interpolatedBox.minZ).color(r, g, b, a).next();
        // this one takes us back to a previous spot, which is why the alpha is 0
        buffer.vertex(interpolatedBox.minX, interpolatedBox.minY, interpolatedBox.maxZ).color(r, g, b, 0f).next();
        buffer.vertex(interpolatedBox.minX, interpolatedBox.maxY, interpolatedBox.maxZ).color(r, g, b, a).next();
        // Same with this one
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.minY, interpolatedBox.maxZ).color(r, g, b, 0f).next();
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.maxY, interpolatedBox.maxZ).color(r, g, b, a).next();
        // aaaand this one
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.minY, interpolatedBox.minZ).color(r, g, b, 0f).next();
        buffer.vertex(interpolatedBox.maxX, interpolatedBox.maxY, interpolatedBox.minZ).color(r, g, b, a).next();
        tessellator.draw();
    }

    public static void drawFilledBox(Box box, Color color) {
        final Box interpolatedBox = RenderUtil.interpolateBox(box);

        final BufferBuilder buffer = tessellator.getBuffer();

        RenderUtil.glColor(color);

        // this is pain
        buffer.begin(5, VertexFormats.POSITION);
        WorldRenderer.drawBox(buffer,
                interpolatedBox.minX, interpolatedBox.minY, interpolatedBox.minZ,
                interpolatedBox.maxX, interpolatedBox.maxY, interpolatedBox.maxZ, color.getRed(),
                color.getGreen(), color.getBlue(), color.getAlpha());
        tessellator.draw();
    }

    public static void glColor(final Color color) {
        final float red = color.getRed() / 255F;
        final float green = color.getGreen() / 255F;
        final float blue = color.getBlue() / 255F;
        final float alpha = color.getAlpha() / 255F;

        RenderSystem.color4f(red, green, blue, alpha);
    }

    public static Vec3d interpolatePos(Vec3d pos) {
        return pos.subtract(mc.getEntityRenderDispatcher().camera.getPos());
    }

    public static Box interpolateBox(Box box) {
        return box.offset(mc.getEntityRenderDispatcher().camera.getPos().multiply(-1));
    }

    public static void startRender(MatrixStack matrixStack) {
        RenderSystem.multMatrix(matrixStack.peek().getModel());
        //RenderSystem.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableBlend();
        //RenderSystem.disableLighting();
        RenderSystem.disableCull();
        // Reset the color here so it doesn't end up breaking things later
        RenderSystem.color4f(-1, -1, -1, -1);
    }


    public static void endRender() {
        RenderSystem.color4f(-1, -1, -1, -1);
        RenderSystem.enableCull();
        RenderSystem.enableLighting();
        RenderSystem.disableBlend();
    }


    public static Box getEntityRenderBox(Entity entity, float tickDelta) {
        final double x = entity.lastRenderX + ((entity.getPos().x - entity.lastRenderX) * tickDelta);
        final double y = entity.lastRenderY + ((entity.getPos().y - entity.lastRenderY) * tickDelta);
        final double z = entity.lastRenderZ + ((entity.getPos().z - entity.lastRenderZ) * tickDelta);

        final Box entityBox = entity.getBoundingBox();

        final Vec3d pos = entity.getPos();

        return new Box(entityBox.minX - pos.x + x,
                entityBox.minY - pos.y + y,
                entityBox.minZ - pos.z + z,
                entityBox.maxX - pos.x + x,
                entityBox.maxY - pos.y + y,
                entityBox.maxZ - pos.z + z);
    }
}
