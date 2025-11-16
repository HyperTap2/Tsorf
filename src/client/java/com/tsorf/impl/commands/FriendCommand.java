package com.tsorf.impl.commands;

import com.tsorf.api.command.BaseCommand;
import com.tsorf.api.command.CommandInfo;
import com.tsorf.api.repository.friend.Friend;
import com.tsorf.common.util.player.ChatUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;

@CommandInfo(
        name = "friend",
        description = "Adds friends.",
        aliases = {"f"}
)
public class FriendCommand extends BaseCommand {
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("player", EntityArgumentType.player())
            .executes(context -> {
                ClientPlayerEntity player = mc.player;
                if (friendRepository.isFriend(player.getUuid())) {
                    friendRepository.removeFriend(player.getUuid());
                    ChatUtils.addChatMessage("Removed " + player.getGameProfile().getName() + " from friends");
                } else {
                    friendRepository.addFriend(new Friend(player.getUuid(), player.getGameProfile().getName()));
                    ChatUtils.addChatMessage("Added " + player.getGameProfile().getName() + " to friends");
                }
                return 1;
            }));
    }
}
