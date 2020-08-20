package io.github.vialdevelopment.tori.client.modules.render;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.module.Category;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.events.RenderScreenObstructionEvent;

public class NoRenderModule extends Module {

    public NoRenderModule() {
        super("NoRender", "Stops the rendering of bothersome overlays", Category.RENDER);
    }

    @Attend
    private final Attender<RenderScreenObstructionEvent> event = new Attender<>(RenderScreenObstructionEvent.class, event -> event.setCanceled(true));
}
