package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.StagedEvent;
import net.minecraft.client.util.math.MatrixStack;

public class Render3DEvent extends StagedEvent {
    public float tickDelta;

    public MatrixStack matrixStack;

    public Render3DEvent(MatrixStack matrixStack, float tickDelta) {
        this.matrixStack = matrixStack;
        this.tickDelta = tickDelta;
    }
}
