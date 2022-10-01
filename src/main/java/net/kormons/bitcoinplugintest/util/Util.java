package net.kormons.bitcoinplugintest.util;

import net.kormons.bitcoinplugintest.BitCoinPluginTest;

import java.io.IOException;

public class Util {

    private static BitCoinPluginTest instance = BitCoinPluginTest.getInstance();

    public static void saveBitCoinData() {

        try {
            instance.getCustomConfig().save(instance.getCoinConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getBitCoinTaskDelay() {

        instance.reloadConfig();
        int delay = instance.getConfig().getInt("변동시간");
        return delay;

    }

    public static int maxVariablePrice() {

        int max = instance.getConfig().getInt("최대변동가");
        return max;

    }

    public static int minVariablePrice() {

        int min = instance.getConfig().getInt("최소변동가");
        return min;

    }

    public static int getPrice() {

        int price = instance.getConfig().getInt("현재가");
        return price;
    }


}
