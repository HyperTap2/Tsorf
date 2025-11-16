
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.other;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;
import com.tsorf.common.util.math.MathUtils;
import com.tsorf.impl.events.player.TickEvent;
import net.minecraft.entity.player.PlayerEntity;

@ModuleInfo(
        name = "Insults",
        description = "Insults Dead Enemies",
        category = ModuleCategory.OTHER
)

public class Insults extends BaseModule implements QuickImports {

    public String[] insults = new String[]{
            " me > u",
            " ez lol",
            " ez nn",
            " resolved hahaha",
            " gg ez",
            " quickdropped LOLOLOL",
            " avg prestige client user",
            " i mog you",
            " bagguette enjoyer",
            " stinky curry muncher",
            " lt6",};
    boolean dead = false;
    boolean targetdead = false;
    PlayerEntity player = null;

    @RegisterEvent
    private void TickEventListener(TickEvent event) {
        if (event.getMode().equals(TickEvent.Mode.PRE)) {
            if (mc.player == null || mc.world == null) return;

            if (player != mc.player.getAttacking() && mc.player.getAttacking() != null) {
                player = (PlayerEntity) mc.player.getAttacking();
                dead = false;
            }
            if (player != null) {
                if (player.isDead() || player.getHealth() == 0) {
                    dead = true;
                    mc.player.networkHandler.sendChatMessage(player.getNameForScoreboard().replaceAll("[^a-zA-Z0-9_]", "") + insults[MathUtils.randomInt(0, insults.length - 1)]);
                }
                if (!player.isPartOfGame()) {
                    dead = false;
                    player = null;
                }
            }
        }
    }
}