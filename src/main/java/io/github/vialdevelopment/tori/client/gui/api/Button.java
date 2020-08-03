package io.github.vialdevelopment.tori.client.gui.api;

import io.github.vialdevelopment.tori.util.FontUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * @author cats
 * @since April 30, 2020
 */
public class Button {

    /**
     * this is text scaling
     */
    public static float textScale = 1.4f;

    public int width, height, x, y;

    /**
     * the text to render on the button
     */
    public String text;

    /**
     * the color of the button
     */
    public Color color;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Button(String text, Color color) {
        this.text = text;
        this.color = color;
    }

    /**
     * Process a click on the button
     */
    public void click(double mouseX, double mouseY, int mouseButton) {

    }

    public void release(double mouseX, double mouseY, int button) {

    }

    /**
     * process a typed key
     */
    public void keyTyped(char typedChar, int keyCode) {

    }

    /**
     * draws the button
     */
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks,  int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        Screen.fill(matrixStack, x, y, x + width, y + height, this.color.getRGB());
        FontUtil.drawShadowedString(matrixStack, this.text,
                x + ((width / 2f)
                        - (FontUtil.getStringWidth(this.text, textScale) / 2)),
                y + 2,
                -1,
                textScale);
    }
}
