
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.visual;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.MultiSetting;
import com.tsorf.api.module.setting.implement.SettingCategory;
import com.tsorf.common.QuickImports;

@ModuleInfo(
    name        = "AntiDebuff",
    description = "Removes Visual Debuffs",
    category    = ModuleCategory.VISUAL
)

public class AntiDebuff extends BaseModule implements QuickImports {
    private final SettingCategory general = new SettingCategory("General");
    public final MultiSetting mode = new MultiSetting(general, "Mode", "Choose Debuffs to hide",
            "Blindness",
            "Darkness",
            "Nausea",
            "Fire",
            "Explosion Particle");
    // handled in a thousand different mixins fuck you microsoft
    public AntiDebuff() {
        getSettingRepository().registerSettings(general, mode);
    }

}
