package chunxi.mplugin.M;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

/* loaded from: test.class */
public class about implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "MPlugin 1.0.2-C");
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "适用版本:1.8.x-1.21.x");
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "作者:纯曦+ZhaYi");
                return false;
            }
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.LIGHT_PURPLE + "MPlugin 1.0.2-C");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "适用版本:1.8.x-1.21.x");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "作者:纯曦+ZhaYi");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "纯曦的B站主页: https://space.bilibili.com/444281310");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "ZhaYi的B站主页: https://space.bilibili.com/1396619405");
            return false;
        }
        return false;
    }
}