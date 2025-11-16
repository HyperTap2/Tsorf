
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.movement;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.player.SlowdownEvent;
import com.tsorf.impl.events.player.TickEvent;

@ModuleInfo(
        name = "NoSlow",
        description = "No Slow Down",
        category = ModuleCategory.MOVEMENT
)
public class NoSlow extends BaseModule implements QuickImports {
    boolean shouldCancel;

    @RegisterEvent
    public void onTick(TickEvent e) {
        if (e.getMode().equals(TickEvent.Mode.PRE)) {
            if (mc.player == null) {
                return;
            }

            shouldCancel = mc.player.getItemUseTimeLeft() % 2 != 0 && mc.player.isUsingItem();
        }
    }

    @RegisterEvent
    public void onSlow(SlowdownEvent eventSlowdown) {
        if (shouldCancel) {
            eventSlowdown.setSlowdown(false);
            mc.player.setSprinting(true);
        }
    }
}