package sc;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class StaffChat extends PluginBase {

    Config cfg;
    private boolean discordChat;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        cfg = getConfig();

        if (cfg.getInt("config-version") != 2) {
            getLogger().warning("Outdated config file!");
        }

        if (getServer().getPluginManager().getPlugin("DiscordChat") != null && !cfg.getString("discord-staff-channel-id").isEmpty()) {
            new DiscordStaffChat(this);
            discordChat = true;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equals("sc") && sender.hasPermission("staff.chat")) {
            if (args.length == 0) {
                return false;
            }

            String msg = cfg.getString("format").replace("%name%", sender.getName()).replace("%message%", String.join(" ", args));

            for (Player p : getServer().getOnlinePlayers().values()) {
                if (p.hasPermission("staff.chat")) {
                    p.sendMessage(msg);
                }
            }

            if (discordChat) {
                me.petterim1.discordchat.API.sendMessage(cfg.getString("discord-staff-channel-id"), TextFormat.clean(msg));
            }
        } else if (cmd.getName().equals("staff") && sender.hasPermission("staff.list")) {
            sender.sendMessage(cfg.getString("staff-list-format"));

            for (Player p : getServer().getOnlinePlayers().values()) {
                if (p.hasPermission("staff.chat")) {
                    sender.sendMessage(p.getName());
                }
            }
        }
        return true;
    }
}
