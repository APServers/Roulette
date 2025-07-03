package net.decentstudio.gamblingaddon;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import noppes.npcs.entity.EntityNPCInterface;

public class TestCommand extends CommandBase {
    @Override
    public String getName() {
        return "testch";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ChallengesManager challengesManager = ChallengesManager.getInstance();
        sender.sendMessage(new TextComponentString("test1"));
        challengesManager.getRoomByPlayer((EntityPlayerMP) sender).ifPresent(room -> {
            sender.sendMessage(new TextComponentString("test2"));
            room.getEntity().ifPresent(entity -> {
                sender.sendMessage(new TextComponentString("test3"));
                EntityNPCInterface npc = (EntityNPCInterface)entity;
                npc.delete();
            });
        });
    }
}
