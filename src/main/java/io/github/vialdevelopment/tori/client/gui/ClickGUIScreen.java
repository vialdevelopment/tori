package io.github.vialdevelopment.tori.client.gui;

import io.github.vialdevelopment.tori.client.gui.api.ClickGUIScene;
import io.github.vialdevelopment.tori.client.gui.scene.module.ModuleScene;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGUIScreen extends Screen implements ParentElement {

    public ClickGUIScreen(Text title) {
        super(title);
    }

    private List<ClickGUIScene> scenes = new ArrayList<>();

    private ClickGUIScene currentScene;

    public void initGUI() {
        this.addScenes();
        this.initScenes();
        this.currentScene = this.scenes.get(0);
    }

    private void addScenes() {
        this.scenes.add(new ModuleScene());
    }

    private void initScenes() {
        for (ClickGUIScene scene : this.scenes) {
            scene.init();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.currentScene.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        this.currentScene.mouseClicked(mouseX, mouseY, mouseButton);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        this.currentScene.mouseReleased(mouseX, mouseY, mouseButton);
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.currentScene.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        this.currentScene.mouseScrolled(mouseX, mouseY, amount);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
