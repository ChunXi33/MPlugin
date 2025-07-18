package chunxi.mplugin.M;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class mgift implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查是否是玩家发送的命令
        if (!(sender instanceof Player)) {
            sender.sendMessage("§d请在游戏中使用这个指令");
            return true;
        }

        Player player = (Player) sender;

        // 检查参数是否正确
        if (args.length != 1) {
            player.sendMessage("§b用法: /mgift <player>");
            return true;
        }

        // 获取目标玩家
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§d玩家 " + args[0] + " 不在这里！");
            return true;
        }

        // 检查目标玩家是否是自己
        if (target.equals(player)) {
            player.sendMessage("§e暂时不支持自己给自己送礼物哦~");
            return true;
        }

        // 获取玩家手中的物品
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage("§c你手上没有物品！");
            return true;
        }

        // 检查目标玩家背包是否有空间
        if (target.getInventory().firstEmpty() == -1) {
            player.sendMessage("§d " + target.getName() + " 的背包满啦，无法赠送物品！");
            return true;
        }

        // 获取物品名称和数量
        String itemName;
        ItemMeta meta = itemInHand.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            // 如果有自定义名称，使用自定义名称
            itemName = meta.getDisplayName();
        } else {
            // 否则使用默认物品名称
            itemName = itemInHand.getType().toString().toLowerCase().replace("_", " ");
        }
        int itemAmount = itemInHand.getAmount();

        // 将物品从玩家手中移除
        player.getInventory().setItemInMainHand(null);

        // 将物品添加到目标玩家背包
        target.getInventory().addItem(itemInHand);

        // 发送提示信息
        player.sendMessage("§a你已将 " + itemAmount + " 个 [" + itemName + "§r§a]赠送给 " + target.getName() + "！");
        target.sendMessage("§d" + player.getName() + " 赠送了你 " + itemAmount + " 个 [" + itemName + "§r§d]！");

        return true;
    }
}