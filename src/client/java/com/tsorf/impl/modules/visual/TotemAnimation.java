
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.visual;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.NumberSetting;
import com.tsorf.api.module.setting.implement.SettingCategory;
import com.tsorf.common.QuickImports;

@ModuleInfo(
    name        = "TotemAnimation",
    description = "Changes Totem Animation Duration",
    category    = ModuleCategory.VISUAL
)
public class TotemAnimation extends BaseModule implements QuickImports {
    private final SettingCategory general = new SettingCategory("General");

    public final NumberSetting<Integer> time = new NumberSetting<>(general, "Reduction", "Change Totem Time", 50, 0, 100);

    public TotemAnimation() {
        getSettingRepository().registerSettings(general, time);
    }
}
