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
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "MPlugin 1.0");
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "适用版本:1.20-1.21");
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "作者:纯曦");
                return false;
            }
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.LIGHT_PURPLE + "MPlugin 1.0");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "适用版本:1.20-1.21");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "作者:纯曦");

            // 创建可点击的链接
            TextComponent link = new TextComponent(ChatColor.LIGHT_PURPLE + "[作者B站主页]");
            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://space.bilibili.com/444281310"));
            link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("点击打开").color(net.md_5.bungee.api.ChatColor.GRAY).create()));

            player.sendMessage(link);
            return false;
        }
        return false;
    }
}