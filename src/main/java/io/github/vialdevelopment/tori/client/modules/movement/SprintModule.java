package io.github.vialdevelopment.tori.client.modules.movement;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;
import io.github.vialdevelopment.tori.client.events.SendMovementPacketEvent;

public class SprintModule extends Module {
    public SprintModule() {
        super("Sprint", "Sprints for you!", Category.MOVEMENT);
    }

    @Attend
    private final Attender<SendMovementPacketEvent> sendMovementPacketEvent = new Attender<>(SendMovementPacketEvent.class, event -> {
        if (mc.player == null) return;
        if (mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0) {
            if (mc.player.getHungerManager().getFoodLevel() > 6) mc.player.setSprinting(true);
        }
    });
}
