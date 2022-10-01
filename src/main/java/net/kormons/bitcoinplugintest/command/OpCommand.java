package net.kormons.bitcoinplugintest.command;

import net.kormons.bitcoinplugintest.BitCoinPluginTest;
import net.kormons.bitcoinplugintest.model.BitCoinManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpCommand implements CommandExecutor {

    public static BitCoinPluginTest instance = BitCoinPluginTest.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (player.isOp()){

            switch (args[0]){

                case "리로드":

                    BitCoinManager.reloadConfig();

                case "셋업":

                    BitCoinManager.setDataConfig(player);
                    player.sendMessage("셋업");

            }

        }
        return false;
    }
}
