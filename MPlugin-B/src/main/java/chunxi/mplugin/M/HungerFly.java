package chunxi.mplugin.M;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HungerFly implements CommandExecutor, Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, BukkitRunnable> tasks = new HashMap<>();
    private int hungerDeductionInterval; // 存储消耗间隔(秒)
    private int minHungerToFly; // 存储允许飞行的最低饥饿值

    public HungerFly(JavaPlugin plugin) {
        this.plugin = plugin;
        // 加载或创建配置文件
        plugin.saveDefaultConfig();
        loadConfig();
        // 注册事件监听器
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void loadConfig() {
        // 创建默认配置
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        // 设置配置头（会显示在文件顶部）
        config.options().header(
                "配置说明:\n" +
                        "HungerFly(饥饿飞行):\n" +
                        "hunger-deduction-interval: 飞行时每x秒消耗1点饱食度\n" +
                        "  最小值: 1 (设为更小值会自动修正为15)\n" +
                        "  默认值: 15\n" +
                        "min-hunger-to-fly: 允许飞行的最低饥饿值\n" +
                        "  最小值: 1 (设为更小值会自动修正为1)\n" +
                        "  最大值: 19 (设为更大值会自动修正为19)\n" +
                        "  默认值: 1\n" +
                        "--------------------------------"
        );

        // 设置默认值
        config.addDefault("hunger-deduction-interval", 15);
        config.addDefault("min-hunger-to-fly", 1);
        config.options().copyDefaults(true);
        plugin.saveConfig();

        // 读取配置
        hungerDeductionInterval = config.getInt("hunger-deduction-interval");
        minHungerToFly = config.getInt("min-hunger-to-fly");

        // 验证值
        if(hungerDeductionInterval < 1) {
            hungerDeductionInterval = 15;
            plugin.getLogger().warning("配置值自动修正: hunger-deduction-interval 不能小于15");
        }

        if(minHungerToFly < 1) {
            minHungerToFly = 1;
            plugin.getLogger().warning("配置值自动修正: min-hunger-to-fly 不能小于1");
        }

        if(minHungerToFly > 19) {
            minHungerToFly = 19;
            plugin.getLogger().warning("配置值自动修正: min-hunger-to-fly 不能大于19");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("hfly")) {
                toggleFlight(player);
                return true;
            }
        } else {
            sender.sendMessage("§d请在游戏中使用这个指令");
        }
        return false;
    }

    private void toggleFlight(Player player) {
        if (player.getAllowFlight()) {
            disableFlight(player);
            player.sendMessage("§a飞行被关掉啦！");
        } else {
            if (player.getFoodLevel() > minHungerToFly) {
                enableFlight(player);
                player.sendMessage("§d你可以飞起来啦！");
                player.sendMessage("§b要注意饱食度哦~");
                player.sendMessage("§e当前飞行消耗间隔: " + hungerDeductionInterval + "秒");
                player.sendMessage("§e最低飞行饥饿值: " + minHungerToFly);
            } else {
                player.sendMessage("§c好饿！飞不动啦@_@！需要至少 " + minHungerToFly + " 点饱食度才能飞行");
                player.sendMessage("§c飞行被关掉啦！");
                player.setFlying(false);
            }
        }
    }

    private void enableFlight(Player player) {
        player.setAllowFlight(true);
        startHungerTask(player);
    }

    private void disableFlight(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
        cancelTask(player);
    }

    private void startHungerTask(Player player) {
        cancelTask(player);

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancelTask(player);
                    return;
                }

                if (player.getAllowFlight() && player.isFlying()) {
                    int foodLevel = player.getFoodLevel();

                    if (foodLevel > minHungerToFly) {
                        player.setFoodLevel(foodLevel - 1);
                        player.sendActionBar("§6飞行消耗了1点饱食度");

                        // 检查是否达到最低饥饿值
                        if (player.getFoodLevel() <= minHungerToFly) {
                            disableFlight(player);
                            player.sendMessage("§c好饿！飞不动啦@_@");
                        }
                    } else {
                        disableFlight(player);
                        player.sendMessage("§c好饿！飞不动啦@_@");
                    }
                } else {
                    cancelTask(player);
                }
            }
        };

        // 使用配置中的间隔时间
        task.runTaskTimer(plugin, 20L * hungerDeductionInterval, 20L * hungerDeductionInterval);
        tasks.put(player.getUniqueId(), task);
    }

    private void cancelTask(Player player) {
        BukkitRunnable task = tasks.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getAllowFlight()) {
            if (event.isFlying()) {
                if (!tasks.containsKey(player.getUniqueId())) {
                    startHungerTask(player);
                }
            } else {
                cancelTask(player);
            }
        }
    }

    // 重新加载配置的方法(可选)
    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
    }
}