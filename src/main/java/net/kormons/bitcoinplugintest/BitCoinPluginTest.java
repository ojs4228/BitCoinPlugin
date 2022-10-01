package net.kormons.bitcoinplugintest;

import net.kormons.bitcoinplugintest.command.OpCommand;
import net.kormons.bitcoinplugintest.command.PlayerCoinCommand;
import net.kormons.bitcoinplugintest.command.tablist.CommandTab;
import net.kormons.bitcoinplugintest.listener.onJoinListener;
import net.kormons.bitcoinplugintest.task.BitCoinPriceChangeTask;
import net.kormons.bitcoinplugintest.util.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class BitCoinPluginTest extends JavaPlugin {

    private static Economy econ = null;
    public static BitCoinPluginTest instance;
    public static Util util;
    public File BitCoinConfigFile;
    private FileConfiguration BitCoinConfig;

    @Override
    public void onEnable() {

        instance = this;

        BitCoinPriceChangeTask task = new BitCoinPriceChangeTask();

        if (!setupEconomy()) {
            System.out.println("이코노미 플러그인을 찾을 수 없습니다.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        /**if (getConfig() == null) {
            setCoing();
        }**/

        createCustomConfig();
        Util.saveBitCoinData();
        saveDefaultConfig();

        Bukkit.getPluginCommand("비트코인").setTabCompleter(new CommandTab());
        this.getCommand("비트코인").setExecutor(new PlayerCoinCommand());
        this.getCommand("비트코인관리").setExecutor(new OpCommand());

        this.getServer().getPluginManager().registerEvents(new onJoinListener(), this);

        System.out.println("플러그인 로드 완료");
        task.bitCoinPriceChange();

    }

    @Override
    public void onDisable() {

        Util.saveBitCoinData();


    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static BitCoinPluginTest getInstance() {
        return instance;
    }

    public File getCoinConfigFile() {
        return this.BitCoinConfigFile;
    }

    public FileConfiguration getCustomConfig() {
        return this.BitCoinConfig;
    }

    private void createCustomConfig() {
        BitCoinConfigFile = new File(getDataFolder(), "coinData.yml");
        if (!BitCoinConfigFile.exists()) {
            BitCoinConfigFile.getParentFile().mkdirs();
            saveResource("coinData.yml", false);
        }

        BitCoinConfig = new YamlConfiguration();
        try {
            BitCoinConfig.load(BitCoinConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reloadCustomConfig(){

        YamlConfiguration.loadConfiguration(getCoinConfigFile());
    }

    private static void setCoing() {

        instance.reloadConfig();
        instance.getConfig().set("변동시간", 60);
        instance.getConfig().set("최대변동가", 100);
        instance.getConfig().set("최소변동가", 100);
        instance.getConfig().set("현재가", 1000);
        instance.saveConfig();

    }
}
