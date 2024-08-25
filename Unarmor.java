package chunxi.mplugin.M;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Unarmor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("这个命令只能在游戏中使用！");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("unarmor")) {
            unarmorPlayer(player);
            player.sendMessage("你的护甲已被放入背包。");
            return true;
        }

        return false;
    }

    private void unarmorPlayer(Player player) {
        PlayerInventory inventory = player.getInventory();

        // 尝试将护甲放入背包
        ItemStack[] armorItems = {
                inventory.getHelmet(),
                inventory.getChestplate(),
                inventory.getLeggings(),
                inventory.getBoots()
        };

        for (ItemStack item : armorItems) {
            if (item != null) {
                if (!player.getInventory().addItem(item.clone()).isEmpty()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "背包已满，部分物品已丢弃。");
                }

                switch (item.getType().name().toLowerCase()) {

                    default:
                        inventory.setHelmet(null);
                        inventory.setChestplate(null);
                        inventory.setLeggings(null);
                        inventory.setBoots(null);
                }
            }
        }
    }
}