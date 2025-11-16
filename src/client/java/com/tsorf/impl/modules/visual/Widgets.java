package com.tsorf.impl.modules.visual;

import cc.polymorphism.eventbus.RegisterEvent;
import tsorf0.Tsorf;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.ModeSetting;
import com.tsorf.api.module.setting.implement.NumberSetting;
import com.tsorf.api.module.setting.implement.SettingCategory;
import com.tsorf.api.system.render.Render2DEngine;
import com.tsorf.api.system.render.font.FontAtlas;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.render.Render2DEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.text.DecimalFormat;

@ModuleInfo(name = "Widgets", description = "Information Widgets", category = ModuleCategory.VISUAL)
public class Widgets extends BaseModule implements QuickImports {
    private final SettingCategory general = new SettingCategory("General");
    private final ModeSetting mode = new ModeSetting(general, "Mode", "Choose widget style", "Tsorf", "Tsorf");
    private final NumberSetting<Integer> xPos = new NumberSetting<>(general, "xPos", "Internal setting", 5, 0, 1920);
    private final NumberSetting<Integer> yPos = new NumberSetting<>(general, "yPos", "Internal setting", 5, 0, 1080);

    @RegisterEvent
    private void render2DListener(Render2DEvent event) {
        DrawContext context = event.getContext();
        MatrixStack matrices = context.getMatrices();
        if (mc.player == null || mc.getDebugHud().shouldShowDebugHud()) return;

        switch (mode.getValue()) {
            case "Tsorf":
                renderTsorf(matrices, context);
                break;
        }
    }

    private void renderTsorf(MatrixStack matrices, DrawContext context) {
        FontAtlas font = Tsorf.getInstance().getFonts().getPoppins();
        DecimalFormat format = new DecimalFormat("#.##");
        float rectHeight = 15;
        float textX = xPos.getValue();
        float textY = yPos.getValue();
        double speed = Math.hypot(mc.player.getX() - mc.player.prevX, mc.player.getZ() - mc.player.prevZ);
        String speedText = "b/s: " + format.format(speed * 20);
        String coordsText = "XYZ: " + Math.round(mc.player.getX()) + " " + Math.round(mc.player.getY()) + " " + Math.round(mc.player.getZ());
        float centerY = textY - (font.getLineHeight(7f) / 2) + (rectHeight / 2);

        //xyz
        Render2DEngine.drawBlurredRoundedRect(matrices, textX, textY, font.getWidth(coordsText, 7f) + 7.5f, rectHeight, 4, 8, new Color(255, 255, 255, 10));
        Render2DEngine.drawRoundedOutline(matrices, textX, textY, font.getWidth(coordsText, 7f) + 7.5f, rectHeight, 4, 1, new Color(200, 200, 200, 75));
        font.render(matrices, coordsText, textX + 2.5f, centerY, 7f, Color.WHITE.getRGB());

        //speed
        Render2DEngine.drawBlurredRoundedRect(matrices, textX + font.getWidth(coordsText, 7f) + 12.5f, textY, font.getWidth(speedText, 7f) + 7.5f, rectHeight, 4, 8, new Color(255, 255, 255, 10));
        Render2DEngine.drawRoundedOutline(matrices, textX + font.getWidth(coordsText, 7f) + 12.5f, textY, font.getWidth(speedText, 7f) + 7.5f, rectHeight, 4, 1, new Color(200, 200, 200, 75));
        font.render(matrices, speedText, textX + font.getWidth(coordsText, 7f) + 15.5f, centerY, 7f, Color.WHITE.getRGB());
        setWidth(font.getWidth(speedText, 7f) + 7.5f + font.getWidth(coordsText, 7f) + 12.5f);
        setHeight(rectHeight);
    }


    public Widgets() {
        setDraggable(true);
        getSettingRepository().registerSettings(general, mode, xPos, yPos);
        
        xPos.setRenderCondition(() -> false);
        yPos.setRenderCondition(() -> false);
    }
}