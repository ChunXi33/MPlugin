package chunxi.mplugin.M;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import chunxi.mplugin.MPlugin;

public class help implements CommandExecutor {
    private final MPlugin plugin;

    // 构造函数，接收插件实例
    public help(MPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 获取配置文件
        FileConfiguration config = plugin.getConfig();

        // 帮助菜单标题
        sender.sendMessage(ChatColor.GOLD + "===== " + ChatColor.GREEN + "MPlugin 帮助菜单" + ChatColor.GOLD + " =====");

        // 必须显示的命令
        sender.sendMessage(ChatColor.YELLOW + "/mhelp" + ChatColor.WHITE + " - 显示帮助菜单");
        sender.sendMessage(ChatColor.YELLOW + "/mabout" + ChatColor.WHITE + " - 显示插件信息");

        // 根据配置文件动态显示可选命令
        if (config.getBoolean("features.unarmor", true)) {
            sender.sendMessage(ChatColor.YELLOW + "/unarmor" + ChatColor.WHITE + " - 脱下护甲");
        }
        if (config.getBoolean("features.url", true)) {
            sender.sendMessage(ChatColor.YELLOW + "/url" + ChatColor.WHITE + " - 发出一个链接");
        }
        if (config.getBoolean("features.Mbeng", true)) {
            sender.sendMessage(ChatColor.YELLOW + "/mbeng" + ChatColor.WHITE + " - 崩掉服务器(doge)");
        }
        if (config.getBoolean("features.hfly", true)) {
            sender.sendMessage(ChatColor.YELLOW + "/hfly" + ChatColor.WHITE + " - 扣除饱食度飞行");
        }

        if (config.getBoolean("features.mgift", true)) {
            sender.sendMessage(ChatColor.YELLOW + "/mgift" + ChatColor.WHITE + " - 赠送手中的物品");
        }

        // 帮助菜单底部
        sender.sendMessage(ChatColor.GOLD + "========================");

        return true;
    }
}