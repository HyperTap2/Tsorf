/**
 * Created: 2/3/2025
 */
package com.tsorf.impl.commands;

import tsorf0.Tsorf;
import com.tsorf.api.command.BaseCommand;
import com.tsorf.api.command.CommandInfo;
import com.tsorf.common.util.player.ChatUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

@CommandInfo(
        name = "prefix",
        description = "Sets the command prefix.",
        aliases = {"p"}
)
public class PrefixCommand extends BaseCommand {

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("prefix", StringArgumentType.word())
                .executes(context -> {
                    String prefix = context.getArgument("prefix", String.class);
                    Tsorf.getInstance().getCommandManager().setPrefix(prefix);
                    ChatUtils.addChatMessage("Changed prefix to " + prefix + ".");
                    return SINGLE_SUCCESS;
                }));
    }
}