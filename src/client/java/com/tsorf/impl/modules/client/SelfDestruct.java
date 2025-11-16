package com.tsorf.impl.modules.client;

import tsorf0.Tsorf;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.BooleanSetting;
import com.tsorf.common.QuickImports;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@ModuleInfo(
        name = "SelfDestruct",
        description = "Cleans up client traces",
        category = ModuleCategory.CLIENT
)
public class SelfDestruct extends BaseModule implements QuickImports {

    private final BooleanSetting clearLatestLog = new BooleanSetting("Clear Logs", "Removes client traces from logs", true);

    public SelfDestruct() {
        getSettingRepository().registerSettings(
                clearLatestLog
        );
    }

    @Override
    protected void onModuleEnabled() {
        Tsorf.getInstance().getConfigManager().saveCurrentProfile();
        Tsorf.getInstance().getConfigManager().shutdown();
//        try {
//            Files.walk(Tsorf.getInstance().getConfigManager().getConfigRoot())
//                    .sorted(Comparator.reverseOrder())
//                    .map(Path::toFile)
//                    .forEach(File::delete);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Tsorf.getInstance().getModuleManager().getModules().forEach(module -> {
            if (module != this) {
                module.onDisable();
                module.setKey(-1);
            }
        });

        if (clearLatestLog.getValue()) {
            try {
                Path logPath = mc.runDirectory.toPath().resolve("logs/latest.log");
                if (Files.exists(logPath)) {
                    List<String> lines = Files.readAllLines(logPath);
                    List<String> cleanedLines = new ArrayList<>();

                    for (String line : lines) {
                        String lowerCase = line.toLowerCase();
                        if (!lowerCase.contains("tsorf") &&
                                !lowerCase.contains("modulemanager") &&
                                !lowerCase.contains("commandrepository")) {
                            cleanedLines.add(line);
                        }
                    }

                    Files.write(logPath, cleanedLines);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mc.setScreen(null);
        System.gc();
    }
}