package io.github.vialdevelopment.tori.client.modules.misc;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.client.events.TickEvent;
import io.github.vialdevelopment.tori.mixin.duck.EntityDuck;
import net.minecraft.client.network.ClientPlayerEntity;

public class PortalChatModule extends Module {
    public PortalChatModule() {
        super("PortalChat", "Lets you talk in a portal because portals are annoying", Category.MISC);
    }

    @Attend
    private final Attender<TickEvent> tickEventAttender = new Attender<>(TickEvent.class, event -> {
        if (mc.player != null) ((EntityDuck) mc.player).setInNetherPortal(false);
    });
}
