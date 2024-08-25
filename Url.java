package chunxi.mplugin.M;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.MalformedURLException;
import java.net.URL;

public class Url implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("控制台不可用.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("正确格式: /url <url>");
            return true;
        }

        String urlString = args[0];

        try {
            URL url = new URL(urlString);

            // 构建 tellraw JSON 字符串
            String json = ChatColor.YELLOW + player.getName() + "发出了一个链接: "+ ChatColor.GREEN + ChatColor.UNDERLINE + urlString ;

            // 发送 tellraw 消息给所有在线玩家
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(json);
            }

        } catch (MalformedURLException e) {
            player.sendMessage("这个链接无效，需要前置http");
        }

        return true;
    }
}