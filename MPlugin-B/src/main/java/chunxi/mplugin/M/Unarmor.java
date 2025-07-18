package chunxi.mplugin.M;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Unarmor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查是否是玩家发送的命令
        if (!(sender instanceof Player)) {
            sender.sendMessage("§d请在游戏中使用这个指令");
            return true;
        }

        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();

        // 获取护甲装备栏的物品
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();

        // 检查护甲装备栏是否为空
        if (helmet == null && chestplate == null && leggings == null && boots == null) {
            player.sendMessage("§d你的护甲栏已经是空的了！");
            return true;
        }

        // 将护甲装备栏物品转移到背包
        boolean success = true;

        if (helmet != null) {
            if (inventory.addItem(helmet).isEmpty()) {
                inventory.setHelmet(null); // 清空头盔槽
            } else {
                success = false;
                player.sendMessage("§c背包已满！");
            }
        }

        if (chestplate != null && success) {
            if (inventory.addItem(chestplate).isEmpty()) {
                inventory.setChestplate(null); // 清空胸甲槽
            } else {
                success = false;
                player.sendMessage("§c背包已满！");
            }
        }

        if (leggings != null && success) {
            if (inventory.addItem(leggings).isEmpty()) {
                inventory.setLeggings(null); // 清空护腿槽
            } else {
                success = false;
                player.sendMessage("§c背包已满！");
            }
        }

        if (boots != null && success) {
            if (inventory.addItem(boots).isEmpty()) {
                inventory.setBoots(null); // 清空靴子槽
            } else {
                success = false;
                player.sendMessage("§c背包已满！");
            }
        }

        if (success) {
            player.sendMessage("§a护甲栏的物品全部放进背包啦！");
        }

        return true;
    }
}