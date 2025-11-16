package com.tsorf.common.util.player;

import com.tsorf.api.system.render.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.awt.*;

public class ChatUtils {
    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    public static void addChatMessage(String message) {
        if (mc.player == null || mc.world == null) return;
        mc.inGameHud.getChatHud().addMessage(Text.literal(message));
    }

    public static void sendFormattedMessage(String message) {
        if (mc.player == null || mc.world == null) return;

        MutableText arrow = Text.empty();
        String prefix = "Tsorf » ";
        Color primaryColor = new Color(184, 112, 242);
        Color secondaryColor = Color.white;

        for (int i = 0; i < prefix.length(); i++) {
            int interpolatedColor = ColorUtils.interpolateColor(primaryColor, secondaryColor, 5, i * (prefix.length() * 3)).getRGB();
            TextColor textColor = TextColor.fromRgb(interpolatedColor);
            arrow.append(Text.literal(String.valueOf(prefix.charAt(i))).setStyle(
                    Style.EMPTY.withColor(textColor).withBold(true)
            ));
        }

        MutableText fullMessage = arrow.append(Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.WHITE)));
        mc.inGameHud.getChatHud().addMessage(fullMessage);
    }

}
