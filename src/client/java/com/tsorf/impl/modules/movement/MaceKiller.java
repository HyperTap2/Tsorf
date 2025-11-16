
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.movement;

import cc.polymorphism.eventbus.EventPriority;
import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.player.SwingEvent;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

@ModuleInfo(
        name = "MaceKiller",
        description = "Modifies Positions With Maces to deal maximum damage",
        category = ModuleCategory.MOVEMENT
)
public class MaceKiller extends BaseModule implements QuickImports {
    @RegisterEvent(value = EventPriority.LOW)
    private void TickEventListener(SwingEvent event) {
        if (mc.player == null || mc.world == null) return;
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 10, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false, mc.player.horizontalCollision));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.0001, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false, mc.player.horizontalCollision));

    }

}
