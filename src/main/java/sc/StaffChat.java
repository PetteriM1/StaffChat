package sc;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class StaffChat extends PluginBase {

    Config c;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        c = getConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (cmd.getName().equalsIgnoreCase("sc")) {
            for (Player p : getServer().getOnlinePlayers().values()) {
                if (p.hasPermission("staff.chat")) {
                    p.sendMessage(c.getString("format").replace("%name%", sender.getName()).replace("%message%", String.join(" ", args)));
                }
            }
            return true;
        }
        return true;
    }
}
