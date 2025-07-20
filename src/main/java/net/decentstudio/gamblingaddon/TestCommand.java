package net.decentstudio.gamblingaddon;

import com.narutocraft.NarutoCraft;
import com.narutocraft.ninjaplayer.data.service.INinjaPlayerSession;
import net.decentstudio.gamblingaddon.util.BuilderUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

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
        if (BuilderUtils.ISCLIENT) {
            return;
        }

        EntityPlayerMP player;
        try {
            player = getPlayer(server, sender, args[0]);
        } catch (Exception exception) {
            return;
        }

        System.out.println("test command " + player);
        INinjaPlayerSession ninjaPlayer = NarutoCraft.getSession(player).orElse(null);
        if (ninjaPlayer == null) {
            return;
        }

        int currentBalance = ninjaPlayer.getCasinoPoints();
        int newBalance = currentBalance + 1000;
        ninjaPlayer.setCasinoPoints(newBalance);
        player.sendMessage(new TextComponentString("Your balance has been updated to: " + newBalance));
    }
}
