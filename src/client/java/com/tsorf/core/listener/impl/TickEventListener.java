package com.tsorf.core.listener.impl;

import cc.polymorphism.eventbus.RegisterEvent;
import tsorf0.Tsorf;
import com.tsorf.common.QuickImports;
import com.tsorf.core.listener.Listener;
import com.tsorf.impl.events.player.MoveFixEvent;
import com.tsorf.impl.events.player.SilentRotationEvent;
import com.tsorf.impl.events.player.TickAIEvent;
import com.tsorf.impl.modules.combat.AimAssist;
import com.tsorf.impl.modules.movement.MoveFix;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickEventListener implements Listener, QuickImports {

    private float yaw;
    private float prevYaw;
    private float pitch;
    private float prevPitch = 0;
    public boolean rotating;

    @RegisterEvent
    private void tickAIEventEventListener(TickAIEvent event) {
        if (mc.player == null) return;

        SilentRotationEvent rotation = new SilentRotationEvent(mc.player.getYaw(), mc.player.getPitch());
        Tsorf.getInstance().getEventManager().post(rotation);

        prevYaw = yaw;
        prevPitch = pitch;

        if (rotation.hasBeenModified()) {
            yaw = rotation.getYaw();
            pitch = rotation.getPitch();
            rotating = true;
        } else {
            yaw = mc.player.getYaw();
            pitch = mc.player.getPitch();
            rotating = false;
        }
    }

    @RegisterEvent
    private void handleEvent(MoveFixEvent event) {
        if (!rotating ||
                !Tsorf.getInstance().getModuleManager().isModuleEnabled(MoveFix.class) ||
                Tsorf.getInstance().getModuleManager().getModule(AimAssist.class).getSettingRepository().getSetting("Silent Rotations").getValue().equals(false)) return;
        event.setYaw(yaw);
    }
    

    public float[] getCurrentRotation() {
        return new float[]{yaw, pitch};
    }

    public float[] getPreviousRotation() {
        return new float[]{prevYaw, prevPitch};
    }
}