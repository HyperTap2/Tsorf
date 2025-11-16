package com.tsorf.impl.modules.visual;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.NumberSetting;

@ModuleInfo(
        name = "AspectRatio",
        description = "Changes the aspect ratio of the game",
        category = ModuleCategory.VISUAL
)
public class AspectRatio extends BaseModule {
    private final NumberSetting<Float> aspectRatio = new NumberSetting<>(
        "Aspect Ratio",
        "The aspect ratio of the game",
        1.65f,
        0.1f,
        5.0f
    );
    
    public AspectRatio() {
        getSettingRepository().registerSettings(aspectRatio);
    }
}
