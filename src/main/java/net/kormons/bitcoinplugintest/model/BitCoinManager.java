package net.kormons.bitcoinplugintest.model;

import net.kormons.bitcoinplugintest.BitCoinPluginTest;
import net.kormons.bitcoinplugintest.util.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BitCoinManager {

    private static BitCoinPluginTest instance = BitCoinPluginTest.getInstance();
    private static Economy economy = BitCoinPluginTest.getEconomy();

    public static void setDataConfig(Player targetPlayer){

        instance.getCustomConfig().set("비트코인." + targetPlayer.getUniqueId() + ".닉네임", targetPlayer.getName());
        instance.getCustomConfig().set("비트코인." + targetPlayer.getUniqueId() + ".소유비트코인", 0);
        instance.saveConfig();
        targetPlayer.sendMessage("셋업 함수 실행!");


    }

    public static Boolean buyBitCoin(Player targetPlayer, int coin){

        if(economy.getBalance((OfflinePlayer) targetPlayer) < Util.getPrice()*coin){

            return false;

        }

        economy.withdrawPlayer((OfflinePlayer) targetPlayer, (double) Util.getPrice()*coin);
        addBitCoin(targetPlayer, coin);


        return true;

    }

    public static Boolean sellBitCoin(Player targetPlayer, int coin){

        if(coin < getPlayersBitCoin(targetPlayer)){

            return false;

        }

        if(removeBitCoin(targetPlayer, coin)){

            economy.depositPlayer((OfflinePlayer) targetPlayer, (double) Util.getPrice()*coin);

            return true;
        }

        return false;

    }

    public static int getPlayersBitCoin(Player targetPlayer){

        instance.reloadCustomConfig();
        int bitCoin = instance.getCustomConfig().getInt("비트코인." + targetPlayer.getUniqueId() + ".소유비트코인");
        Util.saveBitCoinData();

        return bitCoin;
    }

    public static void setPlayersBitCoin(Player targetPlayer, int setBitCoin){

        instance.reloadCustomConfig();
        instance.getCustomConfig().set("비트코인." + targetPlayer.getUniqueId() + ".소유비트코인", setBitCoin);
        Util.saveBitCoinData();

    }

    public static void setPrice(int price){

        instance.reloadConfig();
        instance.getConfig().set("현재가", price);
        instance.saveConfig();

    }

    public static void addBitCoin(Player target ,int coin){

        instance.reloadCustomConfig();
        setPlayersBitCoin(target, getPlayersBitCoin(target) + coin);
        Util.saveBitCoinData();

    }

    public static boolean removeBitCoin(Player target ,int coin){

        int count = getPlayersBitCoin(target);

        if(count - coin < 0){
            return false;
        }

        instance.reloadCustomConfig();
        setPlayersBitCoin(target, count - coin);
        Util.saveBitCoinData();
        return true;

    }

    public static void BitCoinInfo(Player player){

        instance.reloadCustomConfig();
        player.sendMessage("---- 비트코인 정보 ----");
        player.sendMessage("소유한 비트코인 :"+getPlayersBitCoin(player));
        player.sendMessage("현재가        :" + Util.getPrice());

    }

    public static void reloadConfig(){
        instance.reloadConfig();
    }




}
