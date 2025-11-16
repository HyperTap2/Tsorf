/**
 * Created: 2/10/2025
 */

package com.tsorf.impl.modules.other;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.network.PacketReceiveEvent;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import java.text.DecimalFormat;

@ModuleInfo(
        name        = "FlagDetector",
        description = "detect setback >.<",
        category    = ModuleCategory.OTHER
)
public class FlagDetector extends BaseModule implements QuickImports {
    @RegisterEvent
    private void packetReceiveEventEventListener(PacketReceiveEvent event) {
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket cPacket) {
            DecimalFormat f = new DecimalFormat("#.##");
            notificationManager.addNotification("Set-back Detected", "Forcefully Teleported", 4000);
            //ChatUtils.sendFormattedMessage("Teleported to -> " + "X: " + f.format(cPacket.change().position().getX()) + " Y: " + f.format(cPacket.change().position().getY()) + " Z: " + f.format(cPacket.change().position().getZ()) + " Yaw " + f.format(cPacket.change().yaw()) + " Pitch " + f.format(cPacket.change().pitch()));
        }
    }
}