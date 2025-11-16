
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.visual;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.render.Render2DEvent;

@ModuleInfo(
        name = "NoToast",
        description = "Hides all minecraft toasts",
        category = ModuleCategory.VISUAL
)
public class NoToast extends BaseModule implements QuickImports {
    @RegisterEvent
    private void Render2DEvent(Render2DEvent event) {
        mc.getToastManager().clear();
    }
}
