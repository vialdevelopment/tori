package io.github.vialdevelopment.tori.client.modules.render;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.TickEvent;

public class ClickGUIModule extends Module {

    public static final ClickGUIModule INSTANCE = new ClickGUIModule();

    public final Setting<Boolean> debug = new Setting<>("Debug", false);

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

    @Attend
    private final Attender<TickEvent> tickEventAttender = new Attender<>(TickEvent.class, event -> {
       if (mc.currentScreen != Tori.INSTANCE.clickGUIScreen) {
           this.setState(false);
       }
    });
}
