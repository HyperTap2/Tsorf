package com.tsorf.impl.modules.client;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.ModeSetting;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.screen.primordial.PrimordialScreen;
import com.tsorf.impl.screen.tsorf.TsorfScreen;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(name = "ClickGUI", description = "Opens an interface for module management", category = ModuleCategory.CLIENT, key = GLFW.GLFW_KEY_INSERT)
public class ClickGUI extends BaseModule implements QuickImports {
    private final ModeSetting mode = new ModeSetting("Mode", "Choose Clickgui to use",
            "Tsorf",
            "Tsorf",
            "Primordial");
    @Override
    protected void onModuleEnabled()
    {
        if (mode.getValue().equals("Tsorf")) {
            mc.setScreen(TsorfScreen.getINSTANCE());
        } else {
            mc.setScreen(PrimordialScreen.getINSTANCE());
        }
    }

    public ClickGUI() {
        getSettingRepository().registerSettings(mode);
    }

    @Override
    public void onDisable() {
        if (mode.getValue().equals("Tsorf") && mc.currentScreen instanceof TsorfScreen) {
            mc.setScreen(null);
        } else if (mode.getValue().equals("Primordial") && mc.currentScreen instanceof PrimordialScreen) {
            mc.setScreen(null);
        }
        super.onDisable();
    }
}