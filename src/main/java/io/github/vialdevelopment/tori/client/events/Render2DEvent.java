package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.StagedEvent;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author cats
 * @since April 23, 2020
 */
public class Render2DEvent extends StagedEvent {

    /**
     * the event's matrixStack
     * Super epic
     */
    public final MatrixStack matrixStack;

    /**
     * The event's partialTicks, used for rendering stuff between ticks
     */
    public final float partialTicks;

    /**
     * Init it and get these things
     * @param partialTicks the partial ticks
     */
    public Render2DEvent(MatrixStack matrixStack, float partialTicks) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
    }
}
