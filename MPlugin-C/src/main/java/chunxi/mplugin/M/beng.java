package chunxi.mplugin.M;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
/* loaded from: test.class */
public class beng implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "请在游戏中使用这个指令");
                return false;
            }
            Player player = (Player) commandSender;
            player.kickPlayer("§d你还真想崩服啊（");


            return false;
        }
        return false;
    }
}