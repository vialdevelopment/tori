package io.github.vialdevelopment.tori.client.modules.render;

import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.module.Category;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.TickEvent;
import io.github.vialdevelopment.tori.client.settings.BooleanSetting;

public class ClickGUIModule extends Module {

    public static final ClickGUIModule INSTANCE = new ClickGUIModule();

    public final BooleanSetting debug = new BooleanSetting("Debug", false);

    public ClickGUIModule() {
        super("ClickGUI", "Displays a gui that you cn click on!", Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        try {
            mc.openScreen(Tori.INSTANCE.clickGUIScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Attender<TickEvent> tickEventAttender = new Attender<>(TickEvent.class, event -> {
       if (mc.currentScreen != Tori.INSTANCE.clickGUIScreen) {
           this.setState(false);
       }
    });
}
