package com.tsorf.impl.modules.other;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;

@ModuleInfo(
        name        = "HorseJump",
        description = "Always Jump perfectly with a horse",
        category    = ModuleCategory.OTHER
)
public class HorseJump extends BaseModule implements QuickImports {

    @RegisterEvent
    private void Render2DEvent(com.tsorf.impl.events.render.Render2DEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.getJumpingMount() != null && mc.options.jumpKey.wasPressed()) {
            mc.player.getJumpingMount().setJumpStrength(100);
        }
    }
}

