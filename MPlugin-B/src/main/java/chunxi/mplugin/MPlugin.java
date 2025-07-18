package chunxi.mplugin;

import chunxi.mplugin.M.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MPlugin extends JavaPlugin {
    private HungerFly hungerFly;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // 确保配置文件存在并加载
        createConfig();

        // 读取配置文件
        config = getConfig();

        // 必须载入的功能
        getCommand("Mabout").setExecutor(new about());
        getCommand("Mhelp").setExecutor(new help(this)); // 传递插件实例

        // 根据配置文件启用可选功能
        if (config.getBoolean("features.unarmor", true)) {
            getCommand("unarmor").setExecutor(new Unarmor());
        }
        if (config.getBoolean("features.url", true)) {
            getCommand("url").setExecutor(new Url());
        }
        if (config.getBoolean("features.Mbeng", true)) {
            getCommand("Mbeng").setExecutor(new beng());
        }

        if (config.getBoolean("features.hfly", true)) {
            hungerFly = new HungerFly(this);
            getCommand("hfly").setExecutor(hungerFly);
        }

        if (config.getBoolean("features.mgift", true)) {
            getCommand("mgift").setExecutor(new mgift());
        }

        Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a插件已启用!");
        Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a在游戏内/mhelp显示帮助");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§9插件已关闭!");
    }

    private void createConfig() {
        // 确保插件的数据文件夹存在
        if (!getDataFolder().exists()) {
            if (!getDataFolder().mkdirs()) {
                Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§c无法创建插件数据文件夹!");
                return;
            }
        }

        File configFile = new File(getDataFolder(), "config.yml");

        // 如果配置文件不存在，则创建并写入默认配置
        if (!configFile.exists()) {
            saveDefaultConfig();
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a默认配置文件已创建!");
        } else {
            // 如果配置文件已存在，则加载它
            reloadConfig();
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a配置文件已加载!");
        }

        // 检查并更新配置文件（例如添加新功能配置）
        updateConfig();
    }

    private void updateConfig() {
        FileConfiguration config = getConfig();

        // 检查并添加 unarmor 配置
        if (!config.contains("features.unarmor")) {
            config.set("features.unarmor", true); // 默认启用 unarmor 功能
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a已添加 unarmor 功能配置!");
        }

        // 检查并添加 url 配置
        if (!config.contains("features.url")) {
            config.set("features.url", true); // 默认启用 url 功能
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a已添加 url 功能配置!");
        }

        // 检查并添加 Mbeng 配置
        if (!config.contains("features.Mbeng")) {
            config.set("features.Mbeng", true); // 默认启用 Mbeng 功能
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a已添加 Mbeng 功能配置!");
        }

        // 检查并添加 hfly 配置
        if (!config.contains("features.hfly")) {
            config.set("features.hfly", true); // 默认启用 hfly 功能
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a已添加 hfly 功能配置!");
        }

        // 检查并添加 mgift 配置
        if (!config.contains("features.mgift")) {
            config.set("features.mgift", true); // 默认启用 mgift 功能
            Bukkit.getConsoleSender().sendMessage("§e[MPlugin]§a已添加 mgift 功能配置!");
        }

        // 保存更新后的配置文件
        saveConfig();
    }
}