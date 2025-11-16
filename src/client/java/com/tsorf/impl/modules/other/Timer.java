/**
 * Created: 2/10/2025
 */

package com.tsorf.impl.modules.other;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.NumberSetting;
import com.tsorf.api.module.setting.implement.SettingCategory;

@ModuleInfo(
        name        = "Timer",
        description = "Speeds up your game",
        category    = ModuleCategory.OTHER
)
public class Timer extends BaseModule {
    private final SettingCategory general = new SettingCategory("General");
    private final NumberSetting<Double> speed = new NumberSetting<>(general, "Speed", "how fast ya shi",  1.0, 0.1, 10.0);

    public Timer() {
        getSettingRepository().registerSettings(general, speed);
    }

    /* handled in RenderTickCounterMixin */
}