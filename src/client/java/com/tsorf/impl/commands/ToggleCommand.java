/**
 * Created: 2/16/2025
 */

package com.tsorf.impl.commands;

import tsorf0.Tsorf;
import com.tsorf.api.command.BaseCommand;
import com.tsorf.api.command.CommandInfo;
import com.tsorf.api.module.Module;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.command.CommandSource.suggestMatching;

@CommandInfo(
        name = "toggle",
        description = "Toggle Modules",
        aliases = {"t"}
)
public class ToggleCommand extends BaseCommand {

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder
            .then(argument("module", StringArgumentType.word())
                    .suggests((context, suggestionsBuilder) -> {
                        List<String> moduleNames = Tsorf.getInstance().getModuleManager().getModules()
                                .stream()
                                .map(module -> module.getName().replace(" ", ""))
                                .collect(Collectors.toList());
                        return suggestMatching(moduleNames, suggestionsBuilder);
                    })
                    .executes(context -> {
                        String moduleName = context.getArgument("module", String.class);
                        Module module = Tsorf.getInstance().getModuleManager().getModuleByName(moduleName);
                        if (module != null) {
                            Tsorf.getInstance().getModuleManager().toggleModule(module.getClass());
                            notificationManager.addNotification("Toggled", "" + module.getName(), 10000);
                        }
                        return SINGLE_SUCCESS;
                    }));
    }
}