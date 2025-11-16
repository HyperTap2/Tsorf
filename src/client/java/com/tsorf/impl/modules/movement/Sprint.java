
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
import com.tsorf.impl.events.player.TickEvent;

@ModuleInfo(
        name = "Sprint",
        description = "Automatically Sprints For You",
        category = ModuleCategory.MOVEMENT
)
public class Sprint extends BaseModule implements QuickImports {
    @RegisterEvent(value = EventPriority.LOW)
    private void TickEventListener(TickEvent event) {
        if (event.getMode().equals(TickEvent.Mode.PRE)) {
            if (mc.player == null || mc.world == null) return;
            mc.options.sprintKey.setPressed(true);
        }
    }
}