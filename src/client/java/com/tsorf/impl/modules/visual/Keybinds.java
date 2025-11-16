package com.tsorf.impl.modules.visual;

import cc.polymorphism.eventbus.RegisterEvent;
import tsorf0.Tsorf;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.Module;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.ModeSetting;
import com.tsorf.api.module.setting.implement.NumberSetting;
import com.tsorf.api.module.setting.implement.SettingCategory;
import com.tsorf.api.system.render.Render2DEngine;
import com.tsorf.api.system.render.font.FontAtlas;
import com.tsorf.common.QuickImports;
import com.tsorf.common.util.player.input.InputUtils;
import com.tsorf.impl.events.render.Render2DEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(name = "KeyBinds", description = "Shows Binded Modules", category = ModuleCategory.VISUAL)
public class Keybinds extends BaseModule implements QuickImports {

    private final SettingCategory general = new SettingCategory("General");
    private final ModeSetting mode = new ModeSetting(general, "Mode", "Choose style", "Chorus", "Chorus");
    private final NumberSetting<Integer> xPos = new NumberSetting<>(general, "xPos", "Internal setting", 5, 0, 1920);
    private final NumberSetting<Integer> yPos = new NumberSetting<>(general, "yPos", "Internal setting", 105, 0, 1080);

    @RegisterEvent
    private void render2DListener(Render2DEvent event) {
        DrawContext context = event.getContext();
        MatrixStack matrices = context.getMatrices();
        if (mc.player == null || mc.world == null || mc.getDebugHud().shouldShowDebugHud()) return;
        switch (mode.getValue()) {
            case "Chorus":
                renderChorus(matrices, context);
                break;
        }
    }

    private void renderChorus(MatrixStack matrices, DrawContext context) {
        FontAtlas interMedium = Tsorf.getInstance().getFonts().getInterMedium();
        FontAtlas interBold = Tsorf.getInstance().getFonts().getInterSemiBold();
        int x = xPos.getValue();
        int y = yPos.getValue();
        int padding = 6;
        List<Module> enabledModules = new ArrayList<>(
                Tsorf.getInstance().getModuleManager().getModules().stream()
                        .filter(module -> module.getKey() != -1)
                        .sorted(Comparator.comparingDouble(m -> -interMedium.getWidth(m.getName() + InputUtils.getKeyName(m.getKey()), 8)))
                        .toList()
        );
        
        float yPos = y;
        if (enabledModules.isEmpty()) return;
        for (Module module : enabledModules) {
            String moduleString = module.getName() + " | " + InputUtils.getKeyName(module.getKey());
            Render2DEngine.drawRoundedBlur(matrices, x, yPos, interMedium.getWidth(moduleString, 8f) + padding, interMedium.getLineHeight() + 3f, 4, 8, new Color(255, 255, 255, 10));
            Render2DEngine.drawRoundedOutline(matrices, x, yPos, interMedium.getWidth(moduleString, 8f) + padding, interMedium.getLineHeight() + 3f, 4, 1, new Color(200, 200, 200, 75));
            if (module == enabledModules.getFirst())
                setWidth(interMedium.getWidth(moduleString, 8f) + padding);
            interMedium.render(matrices, moduleString, x + 3f, yPos + (interMedium.getLineHeight() / 2f) - 3f, 8f, Color.WHITE.getRGB());
            yPos += interMedium.getLineHeight() + 5;
        }
        setHeight(yPos - y);
    }

    public Keybinds() {
        getSettingRepository().registerSettings(general, mode, xPos, yPos);
        setDraggable(true);
        xPos.setRenderCondition(() -> false);
        yPos.setRenderCondition(() -> false);
    }
}