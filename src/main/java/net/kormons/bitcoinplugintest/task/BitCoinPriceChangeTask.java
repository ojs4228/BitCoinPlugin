package net.kormons.bitcoinplugintest.task;

import net.kormons.bitcoinplugintest.BitCoinPluginTest;
import net.kormons.bitcoinplugintest.model.BitCoinManager;
import net.kormons.bitcoinplugintest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class BitCoinPriceChangeTask {

    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    private static BitCoinPluginTest bitCoinPluginTest = BitCoinPluginTest.getInstance();

    public void bitCoinPriceChange() {

        scheduler.scheduleSyncRepeatingTask(bitCoinPluginTest, new Runnable() {

            public void run() {

                Random random = new Random();


                int max = Util.maxVariablePrice();//100
                int min = Util.minVariablePrice();//100
                int price = Util.getPrice();//1000

                int randomMax = price + max;//1100
                int randomMin = price - min;//900

                while (true) {

                    int newPrice = random.nextInt((randomMax - randomMin)+1) + randomMin;

                    if (newPrice > 0) {

                        BitCoinManager.setPrice(newPrice);

                        bitCoinPluginTest.getServer().broadcastMessage("§e비트코인 가격이 변동되었습니다!");
                        bitCoinPluginTest.getServer().broadcastMessage("§e기존가: §f " + price);
                        bitCoinPluginTest.getServer().broadcastMessage("§e현재가: §f " + newPrice);

                        if(price - newPrice < 0){
                            //상승
                            int price2 = newPrice - price;
                            bitCoinPluginTest.getServer().broadcastMessage("§a+§f " + price2);
                            return;

                        }else {
                            //하락
                            int price2 = price - newPrice;
                            bitCoinPluginTest.getServer().broadcastMessage("§c- §f" + price2);
                            return;
                        }
                    }
                }
            }
        }, 0, Util.getBitCoinTaskDelay() * 20L);
    }
}
