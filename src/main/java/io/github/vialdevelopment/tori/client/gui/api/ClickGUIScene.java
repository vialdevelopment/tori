package io.github.vialdevelopment.tori.client.gui.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

/**
 * made to replicate what we might want in a scene
 */
public interface ClickGUIScene {

    default boolean isHovered(final double mouseX, final double mouseY, int width, int height, int x, int y) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    default MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }

    void init();

    void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    void mouseClicked(double mouseX, double mouseY, int mouseButton);

    void mouseReleased(double mouseX, double mouseY, int button);

    void keyPressed(int keyCode, int scanCode, int modifiers);

    void mouseScrolled(double mouseX, double mouseY, double amount);

}
