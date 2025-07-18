package chunxi.mplugin.M;

import java.net.MalformedURLException;
import java.net.URL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/* loaded from: Url.class */
public class Url implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("请在游戏中使用这个指令");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("正确格式： /url <url>");
            return true;
        }
        String urlString = args[0];
        try {
            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "http://" + urlString;
            }
            new URL(urlString);
            String json = ChatColor.YELLOW + player.getName() + "发出了一个链接： " + ChatColor.GREEN + ChatColor.UNDERLINE + urlString;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(json);
            }
            return true;
        } catch (MalformedURLException e) {
            player.sendMessage("这个链接无效，需要前置http");
            return true;
        }
    }
}